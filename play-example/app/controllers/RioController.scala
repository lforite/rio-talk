package controllers

import RIO.RIO
import cats.instances.future._
import models.{CorrelationId, RequestContext}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext

trait RioController extends Results {

  val correlationHeader = "X-Correlation-Id"

  implicit class RIOOps(action: ActionBuilder[Request, AnyContent]) {
    def rioAsync[A](block: A => RIO[Result])(implicit reads: Reads[A], ec: ExecutionContext): Action[AnyContent] =
      action.async { request =>
        (request.body.asJson.map(_.validate[A]) match {
          case Some(JsSuccess(value, _)) => block(value)
          case Some(JsError(_))          => RIO.pure(UnprocessableEntity(Json.obj()))
          case None                      => RIO.pure(UnsupportedMediaType(Json.obj("message" -> "Expected json")))
        }).flatMap(withCorrelationId).run(getContext(request)).recover(recoverErrors)
      }

    def rioAsync()(block: Request[AnyContent] => RIO[Result])(implicit ec: ExecutionContext): Action[AnyContent] =
      action.async { request =>
        block(request).flatMap(withCorrelationId).run(getContext(request)).recover(recoverErrors)
      }

    private def withCorrelationId(result: Result)(implicit ec: ExecutionContext): RIO[Result] = RIO.ask.map { c =>
      result.withHeaders(correlationHeader -> c.correlationId.value)
    }

    private def getContext[A](request: Request[A]): RequestContext = {
      val correlationId = request.headers.get(correlationHeader).getOrElse(scala.util.Random.alphanumeric.take(15).mkString(""))
      RequestContext(CorrelationId(correlationId))
    }

    private val recoverErrors: PartialFunction[Throwable, Result] = {
      case _: Throwable => InternalServerError(Json.obj("message" -> "Internal server error."))
    }
  }
}

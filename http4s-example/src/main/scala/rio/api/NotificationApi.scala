package rio.api

import cats.effect.IO
import org.http4s.AuthedService
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import rio.RIO
import rio.models.{CreateNotification, RequestContext}
import rio.services.NotificationService

case class NotificationApi(service: NotificationService) extends Http4sDsl[IO] {
  val routes = AuthedService[RequestContext, IO] {
    case req @ POST -> Root / "notifications" as context =>
      (for {
        createNotification <- RIO.liftF(req.req.as[CreateNotification])
        notification       <- service.create(createNotification)
        response           <- RIO.liftF(Created(notification))
      } yield response).run(context)
  }

}

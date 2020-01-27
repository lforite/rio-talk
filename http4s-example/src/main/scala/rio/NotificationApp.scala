package rio

import cats.data.{Kleisli, OptionT}
import cats.effect.{IOApp, _}
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.{Router, Server}
import org.http4s.util.CaseInsensitiveString
import org.http4s.{HttpApp, Request, server}
import rio.api.NotificationApi
import rio.models.{CorrelationId, RequestContext}
import rio.services.NotificationServiceImpl

object NotificationApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    RIOExampleApp.resource.use(_ => IO.never).as(ExitCode.Success)

  object RIOExampleApp {

    def httpApp(): HttpApp[IO] = {
      val notificationService = NotificationServiceImpl()
      val notificationApi     = NotificationApi(notificationService)

      val middleware = server.AuthMiddleware(MyMiddle.withContext)

      val services = middleware(notificationApi.routes)

      Router
        .apply[IO](
          "/api" -> services
        )
        .orNotFound
    }

    def resource: Resource[IO, Server[IO]] = {
      val app = httpApp()
      for {
        server <- BlazeServerBuilder[IO].bindHttp(8081).withHttpApp(app).resource
      } yield server
    }
  }

  object MyMiddle {

    private def generateCorrelationId(): CorrelationId = {
      val id = scala.util.Random.alphanumeric.take(10).mkString
      CorrelationId(id)
    }

    private def getCorrelationId(request: Request[IO]): Option[CorrelationId] =
      request.headers.get(CaseInsensitiveString("X-Correlation-Id")).map(_.value).map(CorrelationId)

    val withContext: Kleisli[OptionT[IO, ?], Request[IO], RequestContext] = Kleisli { request =>
      val correlationId = getCorrelationId(request).getOrElse(generateCorrelationId())
      OptionT.pure(RequestContext(correlationId))
    }

  }

}

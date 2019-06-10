package clients

import RIO.RIO
import cats.implicits._
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

trait NotificationClient {
  def sendNotification(): RIO[Unit]
}

@Singleton
class HttpNotificationClient @Inject()(
    wsClient:      WSClient,
    configuration: Configuration
)(implicit ec:     ExecutionContext)
    extends NotificationClient {

  val url: String = configuration.get[String]("app.notification-service.url")

  override def sendNotification(): RIO[Unit] =
    for {
      correlationId <- RIO.ask.map(_.correlationId)
      result        <- RIO.liftF(Future.unit)
    } yield result

  private def sendNotif(): RIO[Unit] = RIO { ctx =>
    wsClient.url(url).withHttpHeaders("X-Correlation-Id" -> ctx.correlationId.value).post(Json.obj()).map(_ => ())
  }
}

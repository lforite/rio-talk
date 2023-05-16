package clients

import RIO.RIO
import cats.implicits._
import javax.inject.{Inject, Singleton}
import models.UserEmail
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

trait NotificationClient {
  def sendNotification(userEmail: UserEmail, message: String): RIO[Unit]
}

@Singleton
class HttpNotificationClient @Inject()(
    wsClient:      WSClient,
    configuration: Configuration
)(implicit ec:     ExecutionContext)
    extends NotificationClient {

  val url: String = configuration.get[String]("app.notification-service.url")

  override def sendNotification(userEmail: UserEmail, message: String): RIO[Unit] = RIO { ctx =>
    wsClient
      .url(s"$url/api/notifications")
      .withHttpHeaders("X-Correlation-Id" -> ctx.correlationId.value)
      .post(Json.obj("content" -> message, "email" -> userEmail.value))
      .map(_ => ())
  }

}

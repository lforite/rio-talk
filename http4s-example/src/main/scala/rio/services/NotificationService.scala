package rio.services

import rio.RIO
import cats.implicits._
import rio.models.{CreateNotification, Notification, NotificationId}

trait NotificationService {
  def create(create: CreateNotification): RIO[Notification]
}

case class NotificationServiceImpl() extends NotificationService {
  override def create(create: CreateNotification): RIO[Notification] =
    (for {
      notificationId <- generateId()
      notification = Notification(notificationId, create.email, create.content)
      _ <- send(notification)
    } yield notification) <* CustomLogger.info("Notification successfully sent")

  private def generateId(): RIO[NotificationId] =
    RIO.pure(NotificationId(scala.util.Random.alphanumeric.take(25).mkString))

  private def send(notification: Notification): RIO[Unit] =
    RIO.unit
}

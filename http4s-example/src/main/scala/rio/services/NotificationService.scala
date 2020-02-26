package rio.services

import cats.implicits._
import rio.RIO
import rio.models.{CreateNotification, Notification, NotificationId}
import rio.repositories.NotificationRepository

trait NotificationService {
  def create(create: CreateNotification): RIO[Notification]
}

case class NotificationServiceImpl(idGeneratorService: IdGeneratorService, repository: NotificationRepository) extends NotificationService {
  override def create(create: CreateNotification): RIO[Notification] =
    (for {
      notificationId <- idGeneratorService.newId().map(NotificationId)
      notification = Notification(notificationId, create.email, create.content)
      _ <- repository.create(notification)
      _ <- send(notification)
    } yield notification) <*
      CustomLogger.info(s"Notification successfully sent")

  private def send(notification: Notification): RIO[Unit] =
    RIO.unit
}

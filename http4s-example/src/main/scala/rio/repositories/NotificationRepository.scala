package rio.repositories

import rio.RIO
import rio.models.Notification

trait NotificationRepository {
  def create(notification: Notification): RIO[Unit]
}

case class PostgresNotificationRepository() extends NotificationRepository {
  override def create(notification: Notification): RIO[Unit] = RIO.unit
}

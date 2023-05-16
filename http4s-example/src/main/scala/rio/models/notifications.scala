package rio.models

import io.circe.{Decoder, Encoder}

case class NotificationId(value:      String) extends AnyVal
case class UserEmail(value:           String) extends AnyVal
case class NotificationContent(value: String) extends AnyVal

case class Notification(
    id:      NotificationId,
    email:   UserEmail,
    content: NotificationContent
)

case class CreateNotification(
    email:   UserEmail,
    content: NotificationContent
)

object CreateNotification {
  implicit val emailDecoder: Decoder[UserEmail]             = Decoder.decodeString.map(UserEmail)
  implicit val contentDecoder: Decoder[NotificationContent] = Decoder.decodeString.map(NotificationContent)
  implicit val decoder: Decoder[CreateNotification]         = Decoder.forProduct2("email", "content")(CreateNotification.apply)
}

object Notification {
  implicit val encoder: Encoder[Notification] = Encoder.forProduct3("id", "email", "content")((n: Notification) => (n.id.value, n.email.value, n.content.value))
}

case class CorrelationId(value: String) extends AnyVal

case class RequestContext(
    correlationId: CorrelationId
)

package models

import play.api.libs.json.{JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

case class UserFirstName(value: String) extends AnyVal
case class UserLastName(value:  String) extends AnyVal
case class UserEmail(value:     String) extends AnyVal
case class UserId(value:        String) extends AnyVal

case class CreateUser(
    firstName: UserFirstName,
    lastName:  UserLastName,
    email:     UserEmail
)

case class User(
    userId:    UserId,
    firstName: UserFirstName,
    lastName:  UserLastName,
    email:     UserEmail
)

object User {
  implicit val writes: Writes[User] = (
    (JsPath \ "id").write[String].contramap[UserId](_.value) and
    (JsPath \ "first_name").write[String].contramap[UserFirstName](_.value) and
    (JsPath \ "last_name").write[String].contramap[UserLastName](_.value) and
    (JsPath \ "email").write[String].contramap[UserEmail](_.value)
  )(unlift(User.unapply))
}

object CreateUser {
  implicit val reads: Reads[CreateUser] = (
    (JsPath \ "first_name").read[String].map(UserFirstName) and
    (JsPath \ "last_name").read[String].map(UserLastName) and
    (JsPath \ "email").read[String].map(UserEmail)
  )(CreateUser.apply _)
}

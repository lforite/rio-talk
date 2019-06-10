package controllers

import cats.implicits._
import javax.inject.{Inject, Singleton}
import models.{CreateUser, UserId}
import play.api.libs.json._
import play.api.mvc._
import services.UserService

import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject()(
    cc:          ControllerComponents,
    userService: UserService
)(implicit ec:   ExecutionContext)
    extends AbstractController(cc) with RioController {

  def post() = Action.rioAsync[CreateUser] { createUser =>
    userService.createUser(createUser).map(u => Ok(Json.toJson(u)))
  }

  def get(id: String): Action[AnyContent] = Action.rioAsync() { _ =>
    userService.get(UserId(id)).map {
      case Some(u) => Ok(Json.toJson(u))
      case None    => NotFound(Json.obj("message" -> s"User with id $id does not exist."))
    }
  }
}

package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class HealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def get() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.obj("status" -> "healthy"))
  }
}

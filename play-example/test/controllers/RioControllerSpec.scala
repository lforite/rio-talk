package controllers

import RIO.RIO
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class RioControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "RIOController" should {

    "generate a new correlation id when none is provided" in {
      val controller = new RIOTestController(stubControllerComponents())
      val response   = Await.result(controller.get().apply(FakeRequest(GET, "/")), 10 seconds)

      response.header.status mustBe OK
      response.header.headers.get("X-Correlation-Id") match {
        case Some(_) => succeed
        case None    => fail("Expected a correlation, got none")
      }
    }

    "send the same correlation id it received" in {
      val controller = new RIOTestController(stubControllerComponents())
      val request    = FakeRequest(GET, "/").withHeaders("X-Correlation-Id" -> "abcdefghijklmnopqrst")
      val response   = Await.result(controller.get().apply(request), 10 seconds)

      response.header.status mustBe OK
      response.header.headers.get("X-Correlation-Id") match {
        case Some(correlationId) => correlationId mustBe "abcdefghijklmnopqrst"
        case None                => fail("Expected a correlation, got none")
      }
    }

    class RIOTestController(
        cc: ControllerComponents,
    ) extends AbstractController(cc) with RioController {
      def get(): Action[AnyContent] = Action.rioAsync() { _ =>
        RIO.pure(Ok(Json.obj()))
      }
    }
  }
}

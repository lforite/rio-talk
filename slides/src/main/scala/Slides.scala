import PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Slides extends JSApp {

  import Enumeration._

  val introduction = chapter(
    chapterSlide(
      <.h1("Request tracing in microservices architecture"),
      <.h2("A Kleisli tale")
    )
  )

  val meAndIadvize = chapter(
    chapterSlide(
      <.img(
        ^.src := "./img/iadvize-logo.png",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    ),
    headerSlide(
      "iAdvize",
      Enumeration(
        Item.fadeIn("Based in Nantes"),
        Item.fadeIn("SaaS chat solution"),
        Item.fadeIn("Powerful targeting system"),
        Item.fadeIn("Largest websites in Europe ")
      )
    ),
    headerSlide(
      "iAdvize",
      <.h3("Much fun, such chat, so conversation"),
      <.img(
        ^.src := "./img/louis-chat.png",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    )
  )

  val microservicesArchitecture = chapter(
    chapterSlide(
      <.h1("Microservices architecture")
    ),
    headerSlide(
      "microservices",
      <.img(
        ^.src := "./img/microservices/microservices_architecture.svg",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    ),
    headerSlide(
      "microservices",
      <.img(
        ^.src := "./img/microservices/microservices_architecture_ok.svg",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    ),
    headerSlide(
      "microservices",
      <.img(
        ^.src := "./img/microservices/microservices_architecture.svg",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    ),
    headerSlide(
      "microservices",
      <.img(
        ^.src := "./img/microservices/microservices_architecture_ko.svg",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      )
    ),
    headerSlide(
      "microservices",
      <.a(
        ^.href := "https://github.com/hakimel/reveal.js/",
        "reveal.js"
      )
    ),
    headerSlide(
      "microservices",
      <.a(
        ^.href := "https://www.scala-js.org",
        "ScalaJS"
      )
    )
  )

  val solutions = chapter(
    chapterSlide(
      <.h2("Solutions")
    )
  )

  val kleisli = chapter(
    chapterSlide(
      <.h2("Kleisli?")
    ),

    headerSlide(
      "reveal.js commands",
      <.p("Press 'f' to go full-screen and ESC to see an overview of your slides."),
      <.br,
      <.p("You can navigate to the right and down.")
    ),
    headerSlide(
      "My Header",
      <.h3("Headers everywhere")
    ),
    headerSlide(
      "Enumeration",
      Enumeration(
        Item.stable("always show this item"),
        Item.fadeIn("I fade in"),
        Item.stable("I am also always here")
      )
    ),
    headerSlide(
      "Code, so much code",
      scalaC("""
        def main(args: Array[String]): Unit = {
          println("hello, world")
        }
      """),
      scalaFragment("""
        def moreSideEffects(): Unit = {
          println("pop in")
        }
      """)
    ),
    noHeaderSlide(
      <.h3("Or have a blank slide")
    )
  )

  val summary = chapter(
    chapterSlide(
      <.h2("Wrap up")
    )
  )
  val Talk = ScalaComponent
    .builder[Unit]("Presentation")
    .renderStatic(
      <.div(
        ^.cls := "reveal",
        <.div(
          ^.cls := "slides",
          introduction,
          meAndIadvize,
          microservicesArchitecture,
          solutions,
          kleisli,
          summary
        )
      )
    )
    .build

  @JSExport
  override def main(): Unit =
    Talk().renderIntoDOM(dom.document.body)
}

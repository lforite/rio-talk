import java.time.Instant

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
      <.h1("Self introduction")
    ),
    headerSlide(
      "iAdvize",
      Enumeration(
        Item.fadeIn("Based in Nantes"),
        Item.fadeIn("SaaS chat solution"),
        Item.fadeIn("Powerful targeting system"),
        Item.fadeIn("Largest websites in Europe ")
      ),
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
      <.blockquote(
        """"Houston, 
          |we've got a problem"""".stripMargin,
        ^.fontSize.`xx-large`
      )
    ),
    headerSlide(
      "microservices",
      <.pre(
        rawCode(
          "Bash",
          """#logs of service A
          |INFO [2019-05-29T08:11:16.436Z] - Request successfully processed for client xxx
          |INFO [2019-05-29T08:11:17.964Z] - Request successfully processed for client yyy
          |INFO [2019-05-29T08:11:18.256Z] - Request successfully processed for client zzz
          |ERROR [2019-05-29T08:11:20.064Z] - Request failed for client aaa
          |ERROR [2019-05-29T08:11:20.064Z] - java.lang.HttpRequestException: Something went wrong ! 💥😱
          |	at org.lforite.service-a.HttpClient.getB(HttpClient.scala:12)
          |	at org.lforite.service-a.UnderlyingHttpClient.get(HttpClient.scala)
          |INFO [2019-05-29T08:11:21.415Z] - Request successfully processed for client bbb
          |INFO [2019-05-29T08:11:21.473Z] - Request successfully processed for client ccc""".stripMargin
        ),
        ^.fontSize.large
      )
    ),
    headerSlide(
      "microservices",
      <.pre(
        rawCode(
          "Bash",
          """#logs of service B
            |INFO [2019-05-29T06:10:10.436Z] - Request OK
            |INFO [2019-05-29T07:10:33.576Z] - Request OK
            |INFO [2019-05-29T07:10:33.869Z] - Request OK
            |INFO [2019-05-29T08:22:17.514Z] - Request OK
            |INFO [2019-05-29T08:55:17.948Z] - Request OK
            |INFO [2019-05-29T09:56:17.287Z] - Request OK
            |INFO [2019-05-29T10:57:17.391Z] - Request OK
            |INFO [2019-05-29T09:45:17.964Z] - Request OK
            |ERROR [2019-05-29T13:11:19.064Z] - Unexpected request response: got 500
            |ERROR [2019-05-29T13:11:19.064Z] - java.lang.HttpRequestException: Unexpected request response
            |	at org.lforite.service-b.HttpClient.getD(HttpClient.scala:29)
            |	at org.lforite.service-b.UnderlyingHttpClient.get(UnderlyingHttpClient.scala)
            |INFO [2019-05-29T13:11:21.415Z] - Request OK
            |INFO [2019-05-29T14:11:21.473Z] - Request OK""".stripMargin
        ),
        ^.fontSize.large
      ),
    ),
      headerSlide(
        "microservices",
        <.pre(
          rawCode(
            "Bash",
            """#logs of service D
            |INFO  [2019-05-29T08:10:07.815Z] - Received request to fetch entity D with id xyz
            |ERROR [2019-05-29T08:10:07.816Z] - Request ok
            |INFO  [2019-05-29T08:10:07.817Z] - Received request to fetch entity D with id aaa
            |WARN  [2019-05-29T08:10:09.673Z] - Maximum thread pool size reached 
            |ERROR [2019-05-29T08:11:07.815Z] - Request KO for client 4
            |ERROR [2019-05-29T08:11:07.815Z] - java.lang.JDBCConnectionException: JDBC exception: Connection to database lost
            |java.lang.Exception: JDBC exception: Connection lost. 💥😱
            |	at org.lforite.service-d.SQLClient.getEntityD(SQLClient.scala:57)
            |	at org.lforite.service-b.UnderlyingSQLClient.executeQuery(UnderlyingSQLClient.scala)
            |WARN  [2019-05-29T08:10:07.816Z] - Maximum thread pool size reached 
            |INFO  [2019-05-29T08:10:08.817Z] - Start processing for entity dfe
            |ERROR [2019-05-29T08:10:07.815Z] - Request ok
            |DEBUG [2019-05-29T08:10:10.817Z] - Start processing for entity dfe
            |WARN  [2019-05-29T08:10:18.248Z] - Maximum thread pool size reached
            |INFO  [2019-05-29T08:10:51.248Z] - Start processing for entity yhc
            |WARN  [2019-05-29T08:11:12.157Z] - Maximum thread pool size reached 
            |INFO  [2019-05-29T08:11:12.158Z] - Start processing for entity yhc
            |WARN  [2019-05-29T08:11:13.856Z] - Maximum thread pool size reached 
            |INFO  [2019-05-29T08:12:13.856Z] - Start processing for entity 5
            |WARN  [2019-05-29T08:12:14.328Z] - Maximum thread pool size reached""".stripMargin
          ),
          ^.fontSize.large
        )
      ),
      headerSlide(
        "microservices",
        <.h1("🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️")
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

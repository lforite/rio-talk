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
      <.blockquote(""""Houston, we've got a problem"""", ^.fontSize.`xx-large`)
    ),
    headerSlide(
      "microservices",
      <.pre(
        rawCode(
          "Bash",
          """#logs of service A
          |INFO  [2019-05-29T08:11:16.436Z] - Request successfully processed for client xxx
          |INFO  [2019-05-29T08:11:17.964Z] - Request successfully processed for client yyy
          |INFO  [2019-05-29T08:11:18.256Z] - Request successfully processed for client zzz
          |ERROR [2019-05-29T08:11:20.064Z] - Request failed for client aaa
          |ERROR [2019-05-29T08:11:20.064Z] - java.lang.HttpRequestException: Something went wrong ! 💥😱
          |	at org.lforite.service-a.HttpClient.getB(HttpClient.scala:12)
          |	at org.lforite.service-a.UnderlyingHttpClient.get(HttpClient.scala)
          |INFO  [2019-05-29T08:11:21.415Z] - Request successfully processed for client bbb
          |INFO  [2019-05-29T08:11:21.473Z] - Request successfully processed for client ccc""".stripMargin
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
            |INFO  [2019-05-29T06:10:10.436Z] - Request OK
            |INFO  [2019-05-29T07:10:33.576Z] - Request OK
            |INFO  [2019-05-29T07:10:33.869Z] - Request OK
            |INFO  [2019-05-29T08:22:17.514Z] - Request OK
            |INFO  [2019-05-29T08:55:17.948Z] - Request OK
            |INFO  [2019-05-29T09:56:17.287Z] - Request OK
            |INFO  [2019-05-29T10:57:17.391Z] - Request OK
            |INFO  [2019-05-29T09:45:17.964Z] - Request OK
            |ERROR [2019-05-29T13:11:19.064Z] - Unexpected request response: got 500
            |ERROR [2019-05-29T13:11:19.064Z] - java.lang.HttpRequestException: Unexpected request response
            |	at org.lforite.service-b.HttpClient.getD(HttpClient.scala:29)
            |	at org.lforite.service-b.UnderlyingHttpClient.get(UnderlyingHttpClient.scala)
            |INFO  [2019-05-29T13:11:21.415Z] - Request OK
            |INFO  [2019-05-29T14:11:21.473Z] - Request OK""".stripMargin
        ),
        ^.fontSize.large
      )
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
            |	at org.lforite.service-d.SQLClient.getEntityD(SQLClient.scala:57)
            |	at org.lforite.service-d.UnderlyingSQLClient.executeQuery(UnderlyingSQLClient.scala)
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
      <.h1("🕵️‍♀️🕵️‍♂️️")
    ),
    headerSlide(
      "microservices",
      <.h1(
        "🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️🕵️‍♀️🕵️‍♂️🕵️‍♀️🕵️‍♂️️️️"
      )
    ),
    headerSlide(
      "microservices",
      <.h3("How to trace a request ?")
    )
  )

  val solutions = chapter(
    chapterSlide(
      <.h1("Solutions")
    ),
    headerSlide(
      "tracing requests",
      <.h2("Instrumenting your code")
    ),
    headerSlide(
      "tracing requests",
      Enumeration(
        Item.stable("Many tools"),
        Item.fadeIn("Specific knowledge"),
        Item.fadeIn("Time consuming"),
        Item.fadeIn("One more component to maintain"),
        Item.fadeIn("Black magic"),
        Item.fadeIn("Risky")
      )
    ),
    headerSlide(
      "tracing requests",
      <.h2("Just code ?"),
      <.p("Add a correlation id to all your log traces")
    ),
    headerSlide(
      "tracing requests",
      <.pre(
        rawCode(
          "Bash",
          """#logs of service A
            |INFO  [2019-05-29T08:11:18.256Z] [3jyECXXN] - Request successfully processed for client zzz
            |ERROR [2019-05-29T08:11:20.064Z] [1EgBpH1p] - Request failed for client aaa
            |ERROR [2019-05-29T08:11:20.065Z] [1EgBpH1p] - java.lang.HttpRequestException: Something went wrong ! 💥😱
            |	at org.lforite.service-a.HttpClient.getB(HttpClient.scala:12)
            |	at org.lforite.service-a.UnderlyingHttpClient.get(HttpClient.scala)""".stripMargin
        ),
        ^.fontSize.large
      ),
      <.pre(
        rawCode(
          "Bash",
          """#logs of service B
            |INFO  [2019-05-29T09:45:17.964Z] [yWaQPoG7] - Request OK
            |ERROR [2019-05-29T13:11:19.064Z] [1EgBpH1p] - Unexpected request response: got 500
            |ERROR [2019-05-29T13:11:19.064Z] [1EgBpH1p] - java.lang.HttpRequestException: Unexpected request response
            |	at org.lforite.service-b.HttpClient.getD(HttpClient.scala:29)
            |	at org.lforite.service-b.UnderlyingHttpClient.get(UnderlyingHttpClient.scala)""".stripMargin
        ),
        ^.fontSize.large
      ),
      <.pre(
        rawCode(
          "Bash",
          """#logs of service D
            |WARN  [2019-05-29T08:10:09.673Z] [2eHNKpmf] - Maximum thread pool size reached 
            |ERROR [2019-05-29T08:11:07.815Z] [1EgBpH1p] - Request KO for client 4
            |ERROR [2019-05-29T08:11:07.815Z] [1EgBpH1p] - java.lang.JDBCConnectionException: JDBC exception: Connection to database lost
            |	at org.lforite.service-d.SQLClient.getEntityD(SQLClient.scala:57)
            |	at org.lforite.service-d.UnderlyingSQLClient.executeQuery(UnderlyingSQLClient.scala)""".stripMargin
        ),
        ^.fontSize.large
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Thread MDCs (thread local HashMap)"),
      scalaC(
        """
          |//storing a correlation id in thread local variables
          |MDC.put("correlationId", scala.util.Random.alphanumeric.take(8).mkString)
        """.stripMargin
      ),
      scalaC(
        """
          | val correlationId = MDC.get("correlationId")
        """.stripMargin
      ),
      scalaC(
        """
          |trait Logger {
          |  def error(msg: String, t: Throwable): Unit
          |  def info(msg: String): Unit = {
          |    underlyingLogger.info(s"[${MDC.get("correlationId")}] $msg") 
          |  }
          |  def debug(msg: String): Unit
          |}
        """.stripMargin
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Thread MDCs: summary"),
      <.ul(
        Item.fadeIn("✅ Localised"),
        Item.fadeIn("❌ Does not work with multi-threading"),
        Item.fadeIn("❌ Testing"),
        ^.listStyleType.none
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Argument passing"),
      scalaC(
        """
          |trait ServiceA {
          |  def createA(a: EntityA, cid: CorrelationId): Future[_]
          |}
        """.stripMargin
      ),
      scalaC(
        """
          |trait ClientB {
          |  def getB(bId: EntityBID, cid: CorrelationId): Future[_]
          |}
        """.stripMargin
      ),
      scalaC(
        """
          |case class ServiceA(clientB: ClientB) extends ServiceA {
          |  override def createA(a: EntityA, cid: CorrelationId): Future[_] = {
          |    for {
          |      entityB    <- clientB.getB(idOfB, cid)
          |      processed  <- somePrivateBusinessLogic(entityB, cid)
          |      result     <- someMoreBusinessLogic(processed, cid)
          |      _          <- Future(logger.info(s"Successfully processed entity ${a.id} ", cid))
          |    } yield result
          |  }
          |}""".stripMargin
      )
    ),
    headerSlide(
      "tracing requests",
      scalaC(
        """
          |trait Logger {
          |  def error(msg: String, t: Throwable, cid: CorrelationId): Unit
          |  def info(msg: String, cid: CorrelationId): Unit = {
          |    underlyingLogger.info(s"[$cid] $msg")  
          |  }  
          |  def debug(msg: String, cid: CorrelationId): Unit
          |}
        """.stripMargin
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Argument passing: summary"),
      <.ul(
        Item.fadeIn("✅ No black magic"),
        Item.fadeIn("✅ Multi-threading"),
        Item.fadeIn("✅ Testing"),
        Item.fadeIn("❌ Spread everywhere"),
        Item.fadeIn("❌ Hard to read"),
        ^.listStyleType.none
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Implicit argument passing"),
      scalaC(
        """
          |trait ServiceA {
          |  def createA(a: EntityA)(implicit cid: CorrelationId): Future[_]
          |}
        """.stripMargin
      ),
      scalaC(
        """
          |trait ClientB {
          |  def getB(bId: EntityBID)(implicit cid: CorrelationId): Future[_]
          |}
        """.stripMargin
      ),
      scalaC(
        """
          |case class ServiceA(clientB: ClientB) extends ServiceA {
          |  override def createA(a: EntityA)(implicit cid: CorrelationId): Future[_] = {
          |    for {
          |      entityB    <- clientB.getB(idOfB)
          |      processed  <- somePrivateBusinessLogic(entityB)
          |      result     <- someMoreBusinessLogic(processed)
          |      _          <- Future(logger.info(s"Successfully processed entity ${a.id}"))
          |    } yield result
          |  }
          |}""".stripMargin
      )
    ),
    headerSlide(
      "tracing requests",
      scalaC(
        """
          |trait Logger {
          |  def error(msg: String, t: Throwable)(implicit cid: CorrelationId): Unit
          |  def info(msg: String)(implicit cid: CorrelationId): Unit = {
          |    underlyingLogger.info(s"[$cid] $msg")  
          |  }
          |  def debug(msg: String)(implicit cid: CorrelationId): Unit
          |}
        """.stripMargin
      )
    ),
    headerSlide(
      "tracing requests",
      <.h3("Implicit argument passing: summary"),
      <.ul(
        Item.fadeIn("✅ No black magic"),
        Item.fadeIn("✅ Multi-threading"),
        Item.fadeIn("✅ Testing"),
        Item.fadeIn("❌ Spread everywhere"),
        Item.fadeIn("❌ Still hard to read"),
        ^.listStyleType.none
      )
    )
  )

  val kleisli = chapter(
    chapterSlide(
      <.h1("Kleisli")
    ),
    headerSlide(
      "kleisli",
      <.img(
        ^.src := "./img/kleisli.jpeg",
        ^.minHeight := "50%",
        ^.maxHeight := "50%",
        ^.minWidth := "50%",
        ^.maxWidth := "50%"
      ),
      <.a(
        ^.href := "https://en.wikipedia.org/wiki/Heinrich_Kleisli",
        "https://en.wikipedia.org/wiki/Heinrich_Kleisli"
      )
    ),
    headerSlide(
      "kleisli",
      <.pre(
        rawCode(
          "Scala",
          """
          |case class Kleisli[F[_], -A, B](run: A => F[B])
        """.stripMargin),
        ^.fontSize.`xx-large`
      )
    ),
    headerSlide(
      "kleisli",
      <.h3("Kleisli properties"),
      <.p("If ", f_(), " is a ", keyword("Functor"), " then ", kleisli_f_(), " is also a ", keyword("Functor")),
      <.p(
        "If ",
        f_(),
        " is an ",
        keyword("Applicative"),
        " then ",
        kleisli_f_(),
        " is also an ",
        keyword("Applicative"),
        ^.`class` := "fragment fade-in"
      ),
      <.p("If ", f_(), " is a ", keyword("Monad"), " then ", kleisli_f_(), " is also a ", keyword("Monad"), ^.`class` := "fragment fade-in")
    ),
    headerSlide(
      "kleisli",
      <.h3("Kleisli operations"),
      scalaC("create"),
      scalaC("run"),
      scalaC("ask")
    )
  )

  val future = chapter(
    chapterSlide(
      <.h2("Looking at the future")
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
          future,
          summary
        )
      )
    )
    .build

  @JSExport
  override def main(): Unit =
    Talk().renderIntoDOM(dom.document.body)
}

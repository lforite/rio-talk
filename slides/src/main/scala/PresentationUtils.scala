import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import org.scalajs.dom.html.Span
import org.scalajs.dom.raw.HTMLElement

object PresentationUtil {

  val font = HtmlTag("font")

  val dataBackground      = VdomAttr("data-background")
  val dataBackgroundColor = VdomAttr("data-background-color")
  val dataBackgroundSize  = VdomAttr("data-background-size")
  val dataTrim            = VdomAttr("data-trim") := ""
  val dataNoEscape        = VdomAttr("data-noescape") := ""

  def chapter(slides: TagOf[HTMLElement]*): TagOf[HTMLElement] = <.section(slides: _*)

  def header(text: String, cls: String): TagOf[HTMLElement] =
    <.div(
      ^.cls := cls,
      <.p(text)
    )

  // 100% side-effect full
  private def removeHeader(): Unit = {
    val headerElements = dom.document.getElementsByClassName("slide-header")

    (0 until headerElements.length).foreach { id =>
      val element = headerElements(id)

      element.parentNode.removeChild(element)
    }
  }

  private def cleanSlide(content: TagOf[HTMLElement]): TagOf[HTMLElement] = {
    removeHeader()

    content
  }

  private val ChapterSlideProps = Seq(
    (dataBackgroundColor := "#363633"),
    (dataBackgroundSize := "30%")
  )

  def chapterSlide(content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      (ChapterSlideProps ++: content): _*
    )
  )

  def noHeaderSlide(content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      content: _*
    )
  )

  def headerSlide(headerStr: String, content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      (header(headerStr, "slide-header") +: content): _*
    )
  )

  def rawCode(language: String, codeStr: String): TagOf[HTMLElement] =
    <.code(
      ^.cls := language,
      dataTrim,
      dataNoEscape,
      codeStr
    )

  def bash(codeStr:    String): TagOf[HTMLElement] = <.pre(rawCode("Bash", codeStr))
  def scalaC(codeStr:  String): TagOf[HTMLElement] = <.pre(rawCode("Scala", codeStr))
  def haskell(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Haskell", codeStr))
  def lisp(codeStr:    String): TagOf[HTMLElement] = <.pre(rawCode("Lisp", codeStr))

  private def rawCodeFragment(language: String, codeStr: String): TagOf[HTMLElement] =
    <.pre(
      ^.cls := "fragment fade-in",
      rawCode(language, codeStr)
    )

  def scalaFragment(codeStr:   String): TagOf[HTMLElement] = rawCodeFragment("Scala", codeStr)
  def haskellFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("Haskell", codeStr)
  def lispFragment(codeStr:    String): TagOf[HTMLElement] = rawCodeFragment("Lisp", codeStr)

  object Enumeration {

    object Item {

      def stable(content: TagOf[HTMLElement]): TagOf[HTMLElement] = <.li(content)
      def stable(content: String): TagOf[HTMLElement]             = <.li(<.p(content))
      def fadeIn(content: TagOf[HTMLElement]): TagOf[HTMLElement] = <.li(^.cls := "fragment fade-in", content)
      def fadeIn(content: String): TagOf[HTMLElement]             = <.li(^.cls := "fragment fade-in", <.p(content))
    }

    def apply(head: TagOf[HTMLElement], tail: TagOf[HTMLElement]*): TagOf[HTMLElement] =
      <.ul(
        (head +: tail): _*
      )
  }

  def f_(): VdomTagOf[Span] =
    <.span(
      <.span("F", ^.`class` := "hljs-type"),
      "[_]"
    )

  def kleisli_f_(): VdomTagOf[Span] =
    <.span(
      <.span("Kleisli", ^.`class` := "hljs-type"),
      "[",
      f_(),
      ", _, _]",
      ^.`class` := "hljs-class"
    )

  def keyword(word: String): VdomTagOf[Span] =
    <.span(word, ^.`class` := "hljs-keyword")
}

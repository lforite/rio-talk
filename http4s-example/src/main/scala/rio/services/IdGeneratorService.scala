package rio.services

import cats.effect.IO
import rio.RIO

trait IdGeneratorService {
  def newId(): RIO[String]
}

case class ScalaRandomIdGeneratorService() extends IdGeneratorService {
  override def newId(): RIO[String] = RIO.liftF(IO(scala.util.Random.alphanumeric.take(25).mkString))
}

package services

import RIO.RIO
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

trait IdGeneratorService {
  def newId(): RIO[String]
}

@Inject
class ScalaRandomGeneratorService @Inject()()(implicit ec: ExecutionContext) extends IdGeneratorService {
  override def newId(): RIO[String] = RIO.liftF(Future(scala.util.Random.alphanumeric.take(15).mkString("")))
}

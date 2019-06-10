import cats.data.Kleisli
import models.RequestContext
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

package object RIO {
  type RIO[B] = Kleisli[Future, RequestContext, B]

  object RIO {
    def unit: Kleisli[Future, RequestContext, Unit] = Kleisli.pure(())
    def liftF[B](x: Future[B]): Kleisli[Future, RequestContext, B]                   = Kleisli.liftF[Future, RequestContext, B](x)
    def pure[B](x:  B): Kleisli[Future, RequestContext, B]                           = Kleisli.pure(x)
    def apply[B](f: RequestContext => Future[B]): Kleisli[Future, RequestContext, B] = Kleisli(f)
    def ask: Kleisli[Future, RequestContext, RequestContext] = Kleisli.ask[Future, RequestContext]
  }
}

import cats.data.Kleisli
import cats.effect.IO
import rio.models.RequestContext

package object rio {
  type RIO[B] = Kleisli[IO, RequestContext, B]

  object RIO {
    def unit: Kleisli[IO, RequestContext, Unit] = Kleisli.pure(())
    def liftF[B](x: IO[B]): Kleisli[IO, RequestContext, B]                   = Kleisli.liftF[IO, RequestContext, B](x)
    def pure[B](x:  B): Kleisli[IO, RequestContext, B]                       = Kleisli.pure(x)
    def apply[B](f: RequestContext => IO[B]): Kleisli[IO, RequestContext, B] = Kleisli(f)
    def ask: Kleisli[IO, RequestContext, RequestContext] = Kleisli.ask[IO, RequestContext]
  }
}

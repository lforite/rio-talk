package rio.services

import rio.RIO
import cats.effect.IO

object CustomLogger {

  def debug(message: String): RIO[Unit] = RIO { ctx =>
    IO(println(s"DEBUG [${ctx.correlationId.value}] - $message"))
  }

  def info(message: String): RIO[Unit] = RIO { ctx =>
    IO(println(s"INFO [${ctx.correlationId.value}] - $message"))
  }

  def error(message: String, t: Throwable): RIO[Unit] = RIO { ctx =>
    IO(println(s"ERROR [${ctx.correlationId.value}] - $message, ${t.getMessage}"))
  }
}

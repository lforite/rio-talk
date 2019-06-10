package services

import RIO.RIO

import scala.concurrent.Future

object CustomLogger {
  def debug(message: String): RIO[Unit] = RIO { ctx =>
    Future.successful(println(s"DEBUG [${ctx.correlationId.value}] - $message"))
  }

  def info(message: String): RIO[Unit] = RIO { ctx =>
    Future.successful(println(s"INFO [${ctx.correlationId.value}] - $message"))
  }

  def error(message: String, t: Throwable): RIO[Unit] = RIO { ctx =>
    Future.successful(println(s"ERROR [${ctx.correlationId.value}] - $message, ${t.getMessage}"))
  }
}

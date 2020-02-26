package rio.services

import cats.effect.IO
import org.slf4j.{Logger, LoggerFactory}
import rio.RIO

object CustomLogger {
  val logger: Logger = LoggerFactory.getLogger("application")

  def debug(message: String): RIO[Unit] = RIO { ctx =>
    IO(logger.debug(s"DEBUG [${ctx.correlationId.value}] - $message"))
  }

  def info(message: String): RIO[Unit] = RIO { ctx =>
    IO(logger.info(s"INFO [${ctx.correlationId.value}] - $message"))
  }

  def error(message: String, t: Throwable): RIO[Unit] = RIO { ctx =>
    IO(logger.error(s"ERROR [${ctx.correlationId.value}] - $message, ${t.getMessage}", t))
  }
}

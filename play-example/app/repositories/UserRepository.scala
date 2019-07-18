package repositories

import java.sql.Connection

import RIO.RIO
import anorm.SqlParser._
import anorm._
import javax.inject.{Inject, Singleton}
import models._
import play.api.db.Database

import scala.concurrent.{ExecutionContext, Future}

trait UserRepository {
  def create(user: User): RIO[Unit]

  def get(userId: UserId): RIO[Option[User]]
}

@Singleton
class PostgresUserRepository @Inject()(db: Database)(implicit ec: ExecutionContext) extends UserRepository {
  override def create(user: User): RIO[Unit] = RIO.liftF {
    Future {
      db.withConnection { implicit connection =>
        insert(user)
      }
    }.map(_ => ())
  }

  override def get(userId: UserId): RIO[Option[User]] = RIO.liftF {
    Future {
      db.withConnection { implicit connection =>
        getSingle(userId)
      }
    }
  }

  private def insert(user: User)(implicit connection: Connection): Option[String] =
    SQL"""INSERT INTO users(id, first_name, last_name, email)
         VALUES(${user.userId.value}, ${user.firstName.value}, ${user.lastName.value}, ${user.email.value})""".executeInsert(scalar[String].singleOpt)

  private def getSingle(userId: UserId)(implicit connection: Connection): Option[User] =
    SQL"""SELECT * FROM users WHERE id = ${userId.value}""".as(DBModel.userRowParser.singleOpt)
}

object DBModel {
  import anorm.SqlParser._
  import anorm.{~, _}

  val userRowParser: RowParser[User] = {
    (str("id") ~
    str("first_name") ~
    str("last_name") ~
    str("email")).map {
      case id ~ firstName ~ lastName ~ email =>
        User(UserId(id), UserFirstName(firstName), UserLastName(lastName), UserEmail(email))
    }
  }
}

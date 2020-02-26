package services

import RIO.RIO
import cats.implicits._
import clients.NotificationClient
import javax.inject.{Inject, Singleton}
import models.{CreateUser, User, UserId}
import repositories.UserRepository

import scala.concurrent.ExecutionContext

trait UserService {
  def createUser(createUser: CreateUser): RIO[User]

  def get(id: UserId): RIO[Option[User]]
}

@Singleton
class UserServiceImpl @Inject()(
    idGeneratorService: IdGeneratorService,
    notificationClient: NotificationClient,
    userRepository:     UserRepository
)(implicit ec:          ExecutionContext)
    extends UserService {
  override def createUser(createUser: CreateUser): RIO[User] =
    for {
      newId <- idGeneratorService.newId()
      user  <- RIO.pure(User(UserId(newId), createUser.firstName, createUser.lastName, createUser.email))
      _     <- userRepository.create(user)
      _     <- notificationClient.sendNotification(user.email, s"👋 ${user.email.value}, welcome !")
      _     <- CustomLogger.info(s"Successfully created user with id $newId")
    } yield user

  override def get(id: UserId): RIO[Option[User]] = userRepository.get(id)
}

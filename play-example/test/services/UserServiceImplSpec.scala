package services
import RIO.RIO
import clients.NotificationClient
import models._
import org.scalatest.{MustMatchers, WordSpec}
import repositories.UserRepository

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UserServiceImplSpec extends WordSpec with MustMatchers {

  val requestContext = RequestContext(CorrelationId("Aze7QfplD1vv30l"))

  "createUser" should {
    "return a user with a new id" in {
      val idService: IdGeneratorService          = () => RIO.pure("abc")
      val notificationClient: NotificationClient = () => RIO.unit
      val userRepository: UserRepository = new UserRepository {
        override def create(user: User): RIO[Unit]           = RIO.unit
        override def get(userId:  UserId): RIO[Option[User]] = ???
      }
      val userService = new UserServiceImpl(idService, notificationClient, userRepository)
      val createUser  = CreateUser(UserFirstName("John"), UserLastName("Doe"), UserEmail("john.doe@test.com"))

      val future = userService.createUser(createUser).run(requestContext)
      val result = Await.result(future, 10.seconds)

      result mustBe User(
        UserId("abc"),
        UserFirstName("John"),
        UserLastName("Doe"),
        UserEmail("john.doe@test.com")
      )
    }
  }
}

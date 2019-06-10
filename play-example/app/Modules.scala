import clients.{HttpNotificationClient, NotificationClient}
import com.google.inject.AbstractModule
import repositories.{PostgresUserRepository, UserRepository}
import services._

class Modules extends AbstractModule {
  override def configure(): Unit = {
    bindClients()
    bindRepositories()
    bindServices()
  }

  private def bindServices(): Unit = {
    bind(classOf[UserService]).to(classOf[UserServiceImpl]).asEagerSingleton()
    bind(classOf[IdGeneratorService]).to(classOf[ScalaRandomGeneratorService]).asEagerSingleton()
  }

  private def bindRepositories(): Unit =
    bind(classOf[UserRepository]).to(classOf[PostgresUserRepository]).asEagerSingleton()

  private def bindClients(): Unit =
    bind(classOf[NotificationClient]).to(classOf[HttpNotificationClient]).asEagerSingleton()
}

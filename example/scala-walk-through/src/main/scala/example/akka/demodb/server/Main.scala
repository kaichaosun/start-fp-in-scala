package example.akka.demodb.server
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

object Main extends App {
  private val config: Config = ConfigFactory.load("remote-application.conf")
  private val system = ActorSystem("akka-demo", config);
  val actor = system.actorOf(Props.create(classOf[DemoDb]), "akka-demo-db")
}

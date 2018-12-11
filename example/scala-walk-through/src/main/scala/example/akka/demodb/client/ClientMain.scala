package example.akka.demodb.client
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import example.akka.demodb.server.messages.{GetRequest, SetRequest}

import scala.concurrent.Await
import scala.concurrent.duration._

object ClientMain extends App {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("localsystem")

  val localActor = system.actorOf(Props[DemoDbClient], name = "DemoDbClient")
  localActor ! SetRequest("123", new Integer(123))
  val futureResult = localActor ! GetRequest("123")
//  val result = Await.result(futureResult, 10 seconds)
  println(futureResult)
}

package example.akka.demodb.client

import akka.actor.Actor
import akka.pattern.ask
import akka.util.Timeout
import example.akka.demodb.server.messages.{GetRequest, SetRequest}

import scala.concurrent.duration._

class DemoDbClient extends Actor {
  private implicit val timeout = Timeout(2 seconds)
  private val remoteDb = context.actorSelection("akka.tcp://akka-demo@127.0.0.1:2553/user/akka-demo-db")

  override def receive: Receive = {
    case SetRequest(key, value) =>
      val result = remoteDb ! SetRequest(key, value)
      sender() ! result
    case GetRequest(key) =>
      val result = remoteDb ! GetRequest(key)
      sender() ! result
  }

}
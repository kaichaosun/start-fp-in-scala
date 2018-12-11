package example.akka.demodb.client

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import example.akka.demodb.server.messages.{GetRequest, SetRequest}
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

class DemoDbClientSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("localsystem")

  val localActor = system.actorOf(Props[DemoDbClient], name = "DemoDbClient")
  describe("DemoDbClient"){
    it("should set a value"){
      localActor ? SetRequest("123", new Integer(123))
      val futureResult = localActor ? GetRequest("123")
      val result = Await.result(futureResult, 10 seconds)
      result should equal(123)
    }
  }
}

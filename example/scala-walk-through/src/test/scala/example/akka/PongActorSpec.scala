package example.akka

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

class PongActorSpec extends FunSpecLike with Matchers {
  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  val pongActor = system.actorOf(Props(classOf[PongActor]))


  describe("Pong actor"){
    it("should respond with Pong"){
      val future = pongActor ? "Ping" //uses the implicit timeout
      val result = Await.result(future.mapTo[String], 1 second)
      assert(result == "Pong")
    }
    it("should fail on unknown message"){
      val future = pongActor ? "unknown"
      intercept[Exception]{
        Await.result(future.mapTo[String], 1 second)
      }
    }
  }
}


package example.akka

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.pattern.ask

class PongActorSpec extends FunSpecLike with Matchers {
  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  val pongActor = system.actorOf(Props(classOf[PongActor]))

  def askPong(message: String): Future[String] = (pongActor ? message).mapTo[String]

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

  describe("FutureExamples"){
    it("should print to console"){
      (pongActor ? "Ping").onSuccess({
        case x: String => println(s"replied with: $x")
      })
      Thread.sleep(100)
    }

    it("should work as a monad"){
      askPong("Ping").flatMap(x => {
        println(s"replied with: $x")
        askPong("Ping")
      }).onSuccess({
        case x: String => println(s"replied with: $x")
      })
    }
  }
}


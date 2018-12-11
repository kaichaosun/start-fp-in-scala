package example.akka.demodb
import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import example.akka.demodb.server.DemoDb
import example.akka.demodb.server.messages.SetRequest
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

class DemoDbSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()

  describe("DemoDb"){
    describe("given SetRequest"){
      it("should place key/value into the store"){
        val actorRef = TestActorRef(new DemoDb)
        actorRef ! SetRequest("key", "value")

        val demoDbActor = actorRef.underlyingActor
        demoDbActor.store.get("key") should equal(Some("value"))
      }
    }
  }
}

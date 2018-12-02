package example.akka.demodb

import akka.actor.Actor
import akka.event.Logging
import example.akka.demodb.messages.SetRequest

import scala.collection.mutable

class DemoDb extends Actor {
  val store = new mutable.HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) => {
      log.info("Received SetRequest - key: {} value: {}", key, value)
      store.put(key, value)
    }
    case o => log.info("Received unknown message: {}", o)
  }

}

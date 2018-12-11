package example.akka.demodb.server

import akka.actor.{Actor, Status}
import akka.event.Logging
import example.akka.demodb.server.messages.{GetRequest, KeyNotFoundException, SetRequest, UndefinedRequestException}

import scala.collection.mutable

class DemoDb extends Actor {
  val store = new mutable.HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) =>
      println("he-----------set")
      log.info("Received SetRequest - key: {} value: {}", key, value)
      store.put(key, value)
      sender() ! Status.Success
    case GetRequest(key) =>
      println("he-----------get")
      log.info("Received GetRequest - key: [}", key)
      val response = store.get(key)
      response match {
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(KeyNotFoundException(key))
      }
    case o =>
      println("he-----------ooo")
      log.info("Received unknown message: {}", o)
      sender() ! Status.Failure(UndefinedRequestException(o))
  }

}

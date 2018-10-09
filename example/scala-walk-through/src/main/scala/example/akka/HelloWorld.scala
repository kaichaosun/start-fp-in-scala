package example.akka

import akka.actor.{Actor, ActorSystem, Props}

object HelloWorld extends App {

  case class Hello(who: String)

  class GreetingActor extends Actor {
    def receive = {
      case Hello(who) => println(s"hello, $who")
    }
  }

  val props = Props(new GreetingActor)

  val system = ActorSystem("my-system")
  val ref = system.actorOf(props, "greeter")

  ref ! Hello("Xixi")
  ref ! Hello("Scala")
  ref ! "nonexist"
}

package example.akka

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object HelloWorld2 extends App {

  case class Hello(who: String)

  val greetingBehavior: Behavior[Hello] =
    Behaviors.receiveMessage {
      case Hello(who) =>
        println(s"hello, $who")
        Behaviors.same
    }

  val system = ActorSystem(greetingBehavior, "my-system")

  system ! Hello("Xixi")
  system ! Hello("Scala")

}

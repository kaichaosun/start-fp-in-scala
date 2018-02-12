import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

val future: Future[String] =
  Future(123)
    .map(_ + 1)
    .map(_ * 2)
    .map(_ + "!")

Await.result(future, 1.second)

import cats.instances.function._
import cats.syntax.functor._

val func1: Int => Double =
  (x: Int) => x.toDouble

val func2: Double => Double =
  (y: Double) => y * 2

(func1 andThen func2)(1)

(func1 map func2)(1)  // not working.

(func2(func1(1)))

import scala.language.higherKinds
import cats.Functor
import cats.instances.list._
import cats.instances.option._

val list1 = List(1,2,3)

val list2 = Functor[List].map(list1)(_ * 2)

val option1 = Option(123)
val option2 = Functor[Option].map(option1)(_.toString)

val func = (x: Int) => x+1
val liftFunc = Functor[Option].lift(func)

liftFunc(Option(1))

import cats.instances.function._
import cats.syntax.functor._

val funct1 = (a: Int) => a+1
val funct2 = (a: Int) => a*2
val funct3 = (a: Int) => a+"!"
val funct4 = funct1.map(funct2).map(funct3)

funct4(123)
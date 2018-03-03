import cats.Monad
import cats.instances.option._
import cats.instances.list._

val opt1 = Monad[Option].pure(3)

val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))

val opt3 = Monad[Option].map(opt2)(a => a * 100)


val list1 = Monad[List].pure(3)
val list2 = Monad[List].flatMap(List(1,2,3))(x => List(x, x*10))
val list3 = Monad[List].map(list2)(a => a + 123)


import cats.instances.future._ // for Monad
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

val fm = Monad[Future]

val future = fm.flatMap(fm.pure(1))(x => fm.pure(x + 2))

Await.result(future, 1.second)

import cats.syntax.applicative._
//import cats.implicits._
1.pure[Option]

1.pure[List]

1.pure[Future]

import cats.Monad
import cats.syntax.functor._
import cats.syntax.flatMap._
import scala.language.higherKinds

def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
  for {
    x <- a
    y <- b
  } yield x*x + y*y

sumSquare(Option(3), Option(4))

sumSquare(List(1, 2, 3), List(4, 5))


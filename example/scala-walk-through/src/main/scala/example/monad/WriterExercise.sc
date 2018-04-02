import cats.data.Writer
import cats.syntax.writer._
import cats.instances.vector._
import cats.syntax.applicative._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

type Logged[A] = Writer[Vector[String], A]

def slowly[A](body: => A) =
  try body finally Thread.sleep(1000)

def factorial(n: Int): Int = {
  val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
  println(s"fact $n $ans")
  ans
}

factorial(5)

Await.result(Future.sequence(Vector(
  Future(factorial(3)),
  Future(factorial(3))
)), 5.seconds)

def factorial2(n: Int): Logged[Int] =
  for {
    ans <- if (n == 0) {
      1.pure[Logged]
    } else {
      slowly(factorial2(n - 1).map(_ * n))
    }
    _ <- Vector(s"fact $n $ans").tell
  } yield ans

val Vector((logA, ansA), (logB, ansB)) = Await.result(Future.sequence(Vector(
  Future(factorial2(3).run),
  Future(factorial2(3).run)
)), 5.seconds)
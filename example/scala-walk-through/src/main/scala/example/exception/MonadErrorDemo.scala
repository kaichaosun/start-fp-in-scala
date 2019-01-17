package example.exception

import cats.MonadError
import cats.implicits._
import CustomizeError._
import example.exception.MonadErrorDemo.divide

import scala.util.Try

trait CustomError[A] {
  def errorFromString(str: String): A
  def errorFromThrowable(e: Throwable): A
}

object CustomizeError {
  implicit val ThrowableCustomError = new CustomError[Throwable] {
    override def errorFromString(str: String): Throwable =
      new Exception(str)
    override def errorFromThrowable(e: Throwable): Throwable =
      e
  }
}

object MonadErrorDemo {
  def divide[F[_], E](m: Int, n: Int)(implicit monadError: MonadError[F, E],
      error: CustomError[E]): F[Int] =
    if (n == 0) monadError.raiseError(error.errorFromString("Could not parse JSON String"))
    else monadError.pure(m / n)

  val divideTry = divide[Try, Throwable](10, 0)
}

object Main extends App {
  val tryDivide = divide[Try, Throwable](10, 5)
  val eitherDivide = divide[Either[Throwable, ?], Throwable](10, 0)

  println(tryDivide)

}

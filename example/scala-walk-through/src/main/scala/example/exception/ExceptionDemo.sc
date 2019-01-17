import cats.data.Validated.{Invalid, Valid}
import cats.data.{NonEmptyList, Validated}
import cats.effect.IO

import scala.util.Try

case class User(name: String, address: String)
val userTable = Map(1 -> User("kc", "xi'an"))

// --------------Error Representation--------
// Exception
def divide0(m: Int, n: Int): Int =
  m / n

val result0 = try {
  divide0(10, 0)
} catch {
  case e: Exception => e.getMessage
}
// Note: avoid use exception except calling other API.


// Option
def divide1(m: Int, n: Int): Option[Int] =
  if (n == 0) None
  else Some(m / n)

val result1 = for {
  a <- divide1(10, 3)
  b <- divide1(7, 0)
} yield a + b


// Either
def divide2(m: Int, n: Int): Either[String, Double] =
  if (n == 0) Left("Divident can't be zero")
  else Right(m / n)

val result2 = for {
  a <- divide2(10, 3)
  b <- divide2(7, 0)
} yield a + b

// Try
def divide3(m: Int, n: Int): Try[Int] =
  Try{
    m / n
  }

val result3 = for {
  a <- divide3(10, 0)
  b <- divide3(10, 3)
} yield a + b

// Validated
import cats.implicits._

def divide4(m: Int, n: Int): Validated[NonEmptyList[String], Int] =
  if (n == 0) Invalid(NonEmptyList.of("one error"))
  else Valid(m / n)

// broke in worksheet
// val result4 = (divide4(5, 1), divide4(10, 1)).mapN((_, _))

// MonadError

// IO error handling
case class BusinessException(msg: String) extends Exception(msg)

val boom = IO.raiseError(new BusinessException("error happens"))
//boom.unsafeRunSync()
boom.attempt.map{
  case Left(BusinessException(msg)) => "business error"
  case _ => "other error"
}.unsafeRunSync()

val result5 = IO.delay(10 / 0).flatMap(n => IO(println(n)))
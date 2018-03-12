import cats.syntax.either._

import scala.util.Try

def countPositive(ints: List[Int]) = {
  ints.foldLeft(0.asRight[String])((acc, i) => {

    if (i >= 0) {
      acc.map(_ + i)
    } else {
      Left("Stop at negative number.")
    }
  })
}

countPositive(List(1, 2, 3))

countPositive(List(1, -1, 2))

Either.catchOnly[NumberFormatException]("foo".toInt)
Either.catchNonFatal(sys.error("Badboy"))

Either.fromTry(Try("foo".toInt))
Either.fromOption[String, Int](None, "Badboy")

"error".asLeft[Int].getOrElse(0)

"error".asLeft[Int].orElse(2.asRight[String])

2.asRight[String].ensure("Must be non-negative")(_ > 0)

"error".asLeft[Int].recover {
  case str: String => -1
}

"error".asLeft[Int].recoverWith {
  case str: String => Right(-1)
}

"foo".asLeft[Int].leftMap(_.reverse)
6.asRight[String].bimap(_.reverse, _ * 7)
"bar".asLeft[Int].bimap(_.reverse, _ * 7)

123.asRight[String]
123.asRight[String].swap

for {
  a <- 1.asRight[String]
  b <- 0.asRight[String]
  c <- if (b == 0) "DIV0".asLeft[Int]
  else (a / b).asRight[String]
} yield c * 100

// error handling
sealed trait LoginError extends Product with Serializable

final case class UserNotFount(username: String) extends LoginError

final case class PasswordIncorrect(username: String) extends LoginError

final case object UnexpectedError extends LoginError

case class User(username: String, password: String)

type LoginResult = Either[LoginError, User]

def handleError(error: LoginError): Unit =
  error match {
    case UserNotFount(u) =>
      println(s"User not found: $u")
    case PasswordIncorrect(u) =>
      println(s"Password incorrect: $u")
    case UnexpectedError =>
      println("Unexpected error")
  }

val result1: LoginResult = User("Dave", "passw0rd").asRight

val result2: LoginResult = UserNotFount("dave").asLeft

result1.fold(handleError, println)
result2.fold(handleError, println)
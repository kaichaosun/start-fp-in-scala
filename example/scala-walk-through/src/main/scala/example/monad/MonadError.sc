import cats.MonadError
import cats.instances.either._

type ErrorOr[A] = Either[String, A]

val monadError = MonadError[ErrorOr, String]

val success = monadError.pure(42)

val failure = monadError.raiseError("badboy")

monadError.handleError(failure) {
  case "badboy" =>
    monadError.pure("It's Ok")

  case other =>
    monadError.raiseError(("It's not ok"))
}

import scala.util.Try
import cats.instances.try_._

val exn: Throwable = new RuntimeException("It's all gone wrong")

//exn.raiseError[Try, Int]
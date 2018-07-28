import cats.Semigroupal
import cats.data.Validated
import cats.instances.list._


// error handling: fail-fast and accumula􏰀ng.

type AllErrrorsOr[A] = Validated[List[String], A]

Semigroupal[AllErrrorsOr].product(
  Validated.invalid(List("Error 1")),
  Validated.invalid(List("Error 2"))
)

// Create valid and invalid instances

// option 1
val v1 = Validated.Valid(123)
val i1 = Validated.Invalid(List("Badness"))

// option 2
val v2 = Validated.valid[List[String], Int](123)
val i2 = Validated.invalid[List[String], Int](List("Badness"))

// option 3
import cats.syntax.validated._ // for valid and invalid

val v3 =123.valid[List[String]]
val i3 = List("Badness").invalid[Int]

// option 4
import cats.syntax.applicative._ // for pure
import cats.syntax.applicativeError._ // for raiseError

type ErrorsOr[A] = Validated[List[String], A]
val v4 = 123.pure[ErrorsOr]
val i4 = List("Badness").raiseError[ErrorsOr, Int]

// option 5
Validated.catchOnly[NumberFormatException]("foo".toInt)
Validated.catchNonFatal(sys.error("Badness"))
Validated.fromTry(scala.util.Try("foo".toInt))
Validated.fromEither[String, Int](Left("Badness"))
Validated.fromOption[String, Int](None, "Badness")
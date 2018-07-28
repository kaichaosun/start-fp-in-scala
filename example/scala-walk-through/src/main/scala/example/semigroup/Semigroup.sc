import cats.Semigroupal
import cats.instances.option._

Semigroupal[Option].product(Some(123), Some("abc"))

Semigroupal[Option].product(Some(123), None)

Semigroupal.tuple3(Option(1), Option(2), Option(3))
Semigroupal.tuple3(Option(1), Option(2), None)

import cats.syntax.apply._

(Option(123), Option("abt")).tupled

val add: (Int, Int) => Int = (a, b) => a+b
(Option(1), Option(2)).mapN(add)


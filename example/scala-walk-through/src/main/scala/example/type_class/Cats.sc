import cats.Show
import cats.instances.int._
import cats.instances.string._

val showInt: Show[Int] = Show.apply[Int]

val showString: Show[String] = Show.apply[String]

val intAsString: String = showInt.show(123)

val stringAsString: String = showString.show("hello")

import cats.syntax.show._

val intAsString2 = 123.show

val stringAsString2 = "hello".show

import java.util.Date

implicit val dateShowTest: Show[Date] = new Show[Date] {
  override def show(date: Date): String =
    s"${date.getTime}ms since the epoch."
}

implicit val dateShow: Show[Date] =
  Show.show(date => s"${date.getTime}ms since the epoch.")

import cats.Eq
import cats.instances.int._

val eqInt = Eq[Int]
eqInt.eqv(10,10) // "10" will cause tye mismatch

import cats.syntax.eq._
123 === 123

import cats.instances.option._
Option(1) === Option.empty[Int] //Some(1) === None // value === is not a member of Some[Int]


import java.util.Date
import cats.instances.long._

implicit val dateEq: Eq[Date] = Eq.instance((date1, date2) => {
  date1.getTime === date2.getTime
})

val x = new Date()
val y = new Date()
x === y
x === x

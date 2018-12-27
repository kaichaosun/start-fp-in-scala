import shapeless._

val hlist = 26 :: "Harry" :: HNil

// case class
case class Person(name: String, age: Int)

val gp = Generic[Person]

val harry = Person("Harry", 39)

val hl: String :: Int :: HNil = gp.to(harry)

val p: Person = gp.from(hl)

assert(harry == p)

// tuple
val gpTuple = Generic[Tuple2[String, Int]]
val harryTuple = ("Harry", 39)
val hlTuple: String :: Int :: HNil = gpTuple.to(harryTuple)

val tp: Tuple2[String, Int] = gpTuple.from(hlTuple)

assert(harryTuple == tp)

// natural transformation
import poly._

object choose extends (Set ~> Option) {
  override def apply[T](f: Set[T]) = f.headOption
}

choose(Set(1, 2, 3))

choose(Set())

object size extends Poly1 {
  implicit def caseInt = at[Int](x => 1)
  implicit def caseString = at[String](_.length)
  implicit def caseTuple[T, U](implicit st: Case.Aux[T, Int], su: Case.Aux[U, Int]) =
    at[(T, U)](t => size(t._1) + size(t._2))
}

size(23)

size("foo")

size((23, "foo"))

size(((23, "foo"), 13))

// Aux Pattern
trait Foo[A] {
  type B
  def value: B
}

implicit def fi = new Foo[Int] {
  type B = String
  val value = "Foo"
}

implicit def fs = new Foo[String] {
  type B = Boolean
  val value = false
}

def foo[T](t: T)(implicit f: Foo[T]): f.B = f.value

val resfoo1: String = foo(2)
val resfoo2: Boolean = foo("")

import scalaz._, Scalaz._
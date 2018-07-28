import cats.Monoid
import cats.instances.int._     // for Monoid
import cats.instances.list._    // for Monoid
import cats.instances.string._  // for Monoid
import cats.syntax.apply._
import cats.syntax.semigroup._ // for |+|
//import cats.instances.invariant._

case class Cat(
  name: String,
  yearOfBirth: Int,
  favoriteFoods: List[String]
)
// for imapN
val tupleToCat: (String, Int, List[String]) => Cat =
  Cat.apply _
val catToTuple: Cat => (String, Int, List[String]) = cat => (cat.name, cat.yearOfBirth, cat.favoriteFoods)
implicit val catMonoid: Monoid[Cat] = (
  Monoid[String],
  Monoid[Int],
  Monoid[List[String]]
).imapN(tupleToCat)(catToTuple)


val garfield = Cat("Garfield", 1978, List("Lasagne"))
val heathcliff = Cat("Heathcliff", 1988, List("Junk Food"))
garfield |+| heathcliff
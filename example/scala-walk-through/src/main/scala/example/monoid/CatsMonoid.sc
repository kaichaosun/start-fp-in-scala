import cats.Monoid
import cats.instances.string._
import cats.instances.int._
import example.monoid

Monoid[String].combine("hello", "monoid")

Monoid[String].empty

Monoid[Int].combine(1, 2)

import cats.instances.option._

Monoid[Option[Int]].combine(Some(10), Some(4))

import cats.syntax.semigroup._
val resultString = "Hi" |+| "there" |+| Monoid[String].empty

def add(items: List[Int]): Int =
  items.foldLeft(0)(_ + _)

add(List(1,2,3))

def add2(items: List[Int]): Int =
  items.foldLeft(Monoid[Int].empty)(_ |+| _)

add2(List(2,3,4))

def addOption(items: List[Option[Int]]): Option[Int] =
  items.foldLeft(Monoid[Option[Int]].empty)(_ |+| _)

addOption(List(Some(2), Some(4), None))

def addAll[A](items: List[A])(implicit monoid: Monoid[A]): A=
  items.foldLeft(monoid.empty)(_ |+| _)

addAll(List(Some(2), Some(4), None))
addAll(List(2,3,4))
addAll(List(Some(2), Some(4), Some(3)))  //could not find implicit value for parameter monoid: cats.Monoid[Some[Int]]

case class Order(totalCost: Double, quantity: Double)

import cats.instances.double._
implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
  override def empty =
    Order(Monoid[Double].empty, Monoid[Double].empty)

  override def combine(x: Order, y: Order) =
    Order(x.totalCost |+| y.totalCost, x.quantity |+| y.quantity)
}
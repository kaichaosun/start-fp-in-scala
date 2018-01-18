package example.type_class

import cats.Show
import cats.Eq
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._
import cats.syntax.eq._
import example.type_class.PrintableInstances._
import example.type_class.PrintableSyntax._

final case class Cat(name: String, age: Int, color: String)

object Main extends App {
  implicit val printableCat: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = {
      val name = Printable.format(value.name)
      val age = Printable.format(value.age)
      val color = Printable.format(value.color)
      s"$name is a $age year-old $color cat."
    }
  }

  val cat = new Cat("mimi", 3, "yellow")
  cat.print

  implicit val catShow: Show[Cat] =
    Show.show(cat => s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat.")
  println(cat.show)

  implicit val catEq: Eq[Cat] =
    Eq.instance((c1, c2) => {
      c1.name === c2.name && c1.age === c2.age && c1.color === c1.color
    })

  val c1 = new Cat("mimi", 3, "yellow")
  val c2 = new Cat("mimi", 3, "yellow")
  val c3 = new Cat("kitty", 3, "yellow")

  println(c1 === c2)
  println(c1 === c3)
}


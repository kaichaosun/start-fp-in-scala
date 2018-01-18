package example.type_class

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val printableInt = new Printable[Int] {
    def format(value: Int) = value.toString()
  }

  implicit val printableString = new Printable[String] {
    def format(value: String) = value
  }
}

object Printable {
  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  def print[A](value: A)(implicit p: Printable[A]): Unit =
    println(format(value))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]): Unit =
      println(p.format(value))
  }
}

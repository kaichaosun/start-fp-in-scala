package example.functor

trait Printable[A] {
  self =>

  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      def format(value: B): String =
        self.format(func(value))
    }
}

final case class Box[A](value: A)

object Main2 extends App {
  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)

  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }

  implicit val booleanPrintable: Printable[Boolean] = new Printable[Boolean] {
    def format(value: Boolean): String =
      if (value) "yes" else "no"
  }


  println(format("hello"))
  println(format(true))

//  implicit def boxPrintable[A](implicit p: Printable[A]) = new Printable[Box[A]] {
//    override def format(box: Box[A]): String =
//      p.format(box.value)
//  }

  implicit def boxPrintable2[A](implicit p: Printable[A]) =
    p.contramap[Box[A]](_.value)

  println(format(Box("hello")))
  println(format(Box(true)))

}

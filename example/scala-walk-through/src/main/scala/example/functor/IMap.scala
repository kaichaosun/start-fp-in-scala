package example.functor

trait Codec[A] {
  self =>

  def encode(value: A): String
  def decode(value: String): A
  def imap[B](dec: A => B, enc: B => A): Codec[B] =
    new Codec[B] {
      override def encode(value: B): String =
        self.encode(enc(value))

      override def decode(value: String): B =
        dec(self.decode(value))
    }
}

case class Box2[A](value: A)

object MainImap extends App {
  def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)

  def decode[A](value: String)(implicit c: Codec[A]): A =
    c.decode(value)

  implicit val stringCodec: Codec[String] =
    new Codec[String] {
      def encode(value: String): String = value
      def decode(value: String): String = value
    }

  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)
  println(encode("100"))
  println(decode[String]("100"))
  println(decode[Int]("100"))
  println(encode(100))

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] =
    c.imap[Box[A]](Box(_), _.value)

  implicit val doubleCodec: Codec[Double] =
    stringCodec.imap(_.toDouble, _.toString)
  println(encode(123.4))
  println(decode[Double]("123.4"))

  encode(Box(123.4))
  decode[Box[Double]]("123.4")

}
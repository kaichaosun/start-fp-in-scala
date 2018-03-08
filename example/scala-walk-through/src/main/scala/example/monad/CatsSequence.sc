import cats.implicits._

val x: List[Option[Int]] = List(Some(1), Some(2))

val y: List[Option[Int]] = List(None, Some(2))

x.sequence

y.sequence

val ex: Stream[Either[String, Int]] = Stream(Right(2), Right(3))

ex.sequence


val ey: Stream[Either[String, Int]] = Stream(Right(2), Left("next error"), Left("Other error"))

ey.sequence

val e1: Either[String, Int] = Right(10)
val e2: Either[String, Int] = Left("error")

e1.leftMap[String](x=> s"${x} hehe")
e2.leftMap[String](x=> s"${x} hehe")

val s1 = Stream(1,4,5,7)

s1.find(_>5)
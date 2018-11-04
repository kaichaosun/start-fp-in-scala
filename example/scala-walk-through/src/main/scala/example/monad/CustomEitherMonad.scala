package example.monad

sealed trait CustomEither[A, B] {
  def flatMap(f: B => CustomEither[A, B]): CustomEither[A, B] =
    this match {
      case CustomRight(v) =>
        f(v)
      case _ =>
        this
    }

  def map[C](f: B => C): CustomEither[A, C] =
    this match {
      case CustomRight(v) => CustomRight(f(v))
      case _ => this.asInstanceOf[CustomEither[A, C]]
    }
}

case class CustomLeft[A, B](e: A) extends CustomEither[A, B]
case class CustomRight[A, B](v: B) extends CustomEither[A, B]

object CustomEitherMonad extends App {
  val cusEither1 = for {
    a <- CustomRight[String, Int](1)
    b <- CustomRight[String, Int](2)
  } yield (a + b)

  val stdEither = for {
    a <- Right(1)
    b <- Right(1.1)
  } yield a + b

  val cusEither2 = for {
    a <- Right(1)
    b <- Right(1.1)
  } yield a + b

  println(cusEither1)

  println(s"std Either: ${stdEither}")

  println(s"cus Either 2: ${cusEither2}")
}


sealed trait CustomEither2[A, B] {
  def flatMap[A1 >: A, B1](f: B => CustomEither2[A1, B1]): CustomEither2[A1, B1] =
    this match {
      case CustomRight2(v) =>
        f(v)
      case _ =>
        this.asInstanceOf[CustomEither2[A1, B1]]
    }

  def map[C](f: B => C): CustomEither2[A, C] =
    this match {
      case CustomRight2(v) => CustomRight2(f(v))
      case _ => this.asInstanceOf[CustomEither2[A, C]]
    }
}

case class CustomLeft2[A, B](e: A) extends CustomEither2[A, B]
case class CustomRight2[A, B](v: B) extends CustomEither2[A, B]



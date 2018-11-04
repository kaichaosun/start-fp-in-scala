sealed trait CustomEither2[+A, +B] {
  def flatMap[A1 >: A, B1](f: B => CustomEither2[A1, B1]): CustomEither2[A1, B1] =
    this match {
      case CustomRight2(v) =>
        f(v)
      case _ =>
        this.asInstanceOf[CustomEither2[A1, B1]]
    }

  def map[B1](f: B => B1): CustomEither2[A, B1] =
    this match {
      case CustomRight2(v) => CustomRight2(f(v))
      case _ => this.asInstanceOf[CustomEither2[A, B1]]
    }
}

case class CustomLeft2[+A, +B](e: A) extends CustomEither2[A, B]
case class CustomRight2[+A, +B](v: B) extends CustomEither2[A, B]



val e1 = CustomRight2(10)

val e2 = CustomRight2(1.1)
for {
  a <- CustomRight2(1)
  b <- CustomRight2(1.1)
} yield a + b


//val e3 = CustomRight2(1.1)
//for {
//  a <- CustomRight2(1)
//  b <- CustomLeft2("sss")
//} yield a + b
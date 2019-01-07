sealed trait OptionCustom[+A] {
  def flatMap[B](func: A => OptionCustom[B]): OptionCustom[B] =
    this match {
      case NoneCustom => NoneCustom
      case SomeCustom(a) => func(a)
    }

  def map[B](func: A => B): OptionCustom[B] =
    this match {
      case NoneCustom => NoneCustom
      case SomeCustom(a) => SomeCustom(func(a))
    }
}
case object NoneCustom extends OptionCustom[Nothing]
case class SomeCustom[T](value: T) extends OptionCustom[T]


for {
  a <- SomeCustom(10)
  b <- SomeCustom(a + 2)
} yield b

SomeCustom(10).flatMap(x => SomeCustom(x + 5))


package example.implicittest

class AC(a: Int) {
  implicit val at = a
}

case class CC(a:Int) extends AC(a) {
  implicit val at2 = a
}

object ImplicitCaseClass extends App {

  val c1 = CC(2)

  def f(b: Int)(implicit a: Int) = {
    a + b
  }

  c1 match {
    case c@CC(v) =>
      implicit val b = 10
      println(f(10))
  }
}

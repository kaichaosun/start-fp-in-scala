val pf1 = new PartialFunction[(Int, Int), Int] {
  override def isDefinedAt(x: (Int, Int)) = ???
  override def apply(v1: (Int, Int)) = ???
}

def pf2(x: Int, y: Int): Int = {
  case y != 0 => x / y
  case _ => 0
}
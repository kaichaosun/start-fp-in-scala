trait FooAble {
  def foo(): String
}

trait BazAble {
  def baz(): String = "baz"
}

trait MyFooAble extends FooAble {
  override def foo(): String = "foo imple"
}

class BarWithFooAble {
  self: FooAble with BazAble =>

  def bar() = "Here is the bar => " + foo() + " => " + baz()
}

val barWithFooAndBaz = new BarWithFooAble with MyFooAble with BazAble

barWithFooAndBaz.bar()
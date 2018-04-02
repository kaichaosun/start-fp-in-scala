trait FooAble {
  def foo(): String
}

trait MyFooAble extends FooAble {
  override def foo(): String = "foo imple"
}

class BarWithFooAble {
  self: FooAble =>

  def bar() = "Here is the bar => " + foo()
}

val barWithFoo = new BarWithFooAble with MyFooAble

barWithFoo.bar()
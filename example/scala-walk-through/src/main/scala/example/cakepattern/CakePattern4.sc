trait FooAbleComponent {
  val fooAble: FooAble

  class FooAble {
    def foo() = "foo"
  }
}

trait BazAbleComponent {
  val bazAble: BazAble

  class BazAble {
    def baz() = "baz"
  }
}

class BarUsingFooAndBaz {
  self: FooAbleComponent with BazAbleComponent =>

  def bar() = s"bar calls from ${fooAble.foo()} and ${bazAble.baz()}"
}

val barWithFooAndBaz = new BarUsingFooAndBaz with FooAbleComponent with BazAbleComponent {
  val fooAble = new FooAble()
  val bazAble = new BazAble()
}

barWithFooAndBaz.bar()
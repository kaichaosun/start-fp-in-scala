trait FooAble {
  def foo() = "here is your fool"
}

class BarUsingFooable {
  this: FooAble =>

  def bar() = s"here is your bar, ${foo()}"
}

val barWithFoo = new BarUsingFooable with FooAble

barWithFoo.bar()
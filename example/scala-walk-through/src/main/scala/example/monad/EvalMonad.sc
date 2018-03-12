// eager and memoized
val x = {
  println("Computing x")
  math.random
}

x

x

// lazy and not memoized
def y = {
  println("Computing y")
  math.random
}

y

y

// lazy and memoized
lazy val z = {
  println("Computing z")
  math.random
}

z
z

import cats.Eval

val now = Eval.now(math.random + 1000)
val later = Eval.later(math.random + 2000)
val always = Eval.always(math.random + 3000)
later
always

now.value

later.value

always.value
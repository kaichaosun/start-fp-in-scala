import cats.data.State

val a = State[Int, String] { state =>
  (state, s"The state is $state")
}

val (state, result) = a.run(10).value

val state2 = a.runS(10).value

val result2 = a.runA(10).value
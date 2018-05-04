import cats.data.State

type CalcState[A] = State[List[Int], A]

def evalOne(sym: String): CalcState[Int] =
  sym match {
    case "+" => operator(_ + _)
    case "_" => operator(_ - _)
    case "*" => operator(_ * _)
    case "/" => operator(_ / _)
    case num => operand(num.toInt)
  }

def operand(num: Int): CalcState[Int] =
  State[List[Int], Int] { stack =>
    (num :: stack, num)
  }

def operator(func: (Int, Int) => Int): CalcState[Int] =
  State[List[Int], Int] {
    case a :: b :: tail =>
      val ans = func(a, b)
      (ans :: tail, ans)
    case _ =>
      sys.error("Fail!")
  }


evalOne("42").run(Nil).value

val program = for {
  _ <- evalOne("1")
  _ <- evalOne("2")
  ans <- evalOne("+")
} yield ans

program.run(Nil).value

import cats.syntax.applicative._
def evalAll(input: List[String]): CalcState[Int] =
  input.foldLeft(0.pure[CalcState]) { (a, b) =>
    a.flatMap(_ => evalOne(b))
  }

val programEvalAll = evalAll(List("1", "2", "+", "3", "*"))

programEvalAll.run(Nil).value

val init = 0.pure[CalcState]
init.run(List(10)).value

val program3 = for {
  _ <- evalAll(List("1", "2", "+"))
  _ <- evalAll(List("3", "4", "+"))
  ans <- evalOne("*")
} yield ans

program3.run(Nil).value

def evalInput(input: String): Int =
  evalAll(input.split(" ").toList).runA(Nil).value

evalInput("1 2 * 3 4 + *")
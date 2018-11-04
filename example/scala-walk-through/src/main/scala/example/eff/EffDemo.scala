package example.eff

import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._

object EffDemo extends App {

  val eff1 = fromOption(Some(1))

  eff1.runOption.run

  type Stack = Fx.fx2[Option, StringEither]

  type HasOption[R] = MemberIn[Option, R]
  type StringEither[A] = Either[String, A]
  type HasEither[R] = MemberIn[StringEither, R]

  def program[R: HasOption: HasEither] = for {
    a <- fromOption(Some(1))
    b <- fromEither(Right(2))
  } yield a + b

  println(program[Stack])
  println(program[Stack].asInstanceOf[ImpureAp[_, _, _]].continuation.functions)

  val result = program[Stack].runOption.runEither.run
  println(result)

}

import cats._
import data._
import cats.syntax.all._
import cats.instances.all._
import cats.Eq
import cats.~>
import org.atnos.eff.{Eff, Fx, OptionEffect}
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


delay(1).detach.value

type S = Fx.append[Fx.fx2[Writer[Int, ?], Either[String, ?]], Fx.fx1[Option]]

val e: Eff[S, Int] = OptionEffect.some[S, Int](1)

e.runWriter.runEither.detach

type Stack = Fx.fx3[Writer[Int, ?], Either[String, ?], Option]
val e2: Eff[S, Int] = OptionEffect.some[S, Int](1)
e2.runWriter.runEither.detach
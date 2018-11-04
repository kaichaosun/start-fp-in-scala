import org.atnos.eff.{Eff, Fx, MemberIn}
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._

//// 1. Only one option effect
//type Stack = Fx.fx1[Option]
//type HasOptionEffect[R] = MemberIn[Option, R]
//// compute with this stack
//val map: Map[String, Int] =
//  Map("key1" -> 10, "key2" -> 20)
//
//// get 2 keys from the map and add the corresponding values
//def addKeys[R: HasOptionEffect](key1: String, key2: String): Eff[R, Int] = for {
//  a <- fromOption(map.get(key1))
//  b <- fromOption(map.get(key2))
//} yield a + b
//
//addKeys("key1", "key2").runOption.run

val eff1 = fromOption(Some(1))

eff1.runOption.run

val eff2 = for {
  a <- fromOption(Some(1))
  b <- fromOption(Some(2))
} yield a + b

eff2.runOption.run


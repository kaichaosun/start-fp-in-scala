import org.atnos.eff._
import all._
import cats.Traverse
import syntax.all._
import cats.implicits._

/**
  * Stack declaration
  */
type S = Fx.fx1[String Either ?]

// compute with this stack
val map: Map[String, Int] =
  Map("key1" -> 10, "key2" -> 20)

// get 2 keys from the map and add the corresponding values
def addKeys(key1: String, key2: String): Eff[S, Int] = for {
  a <- optionEither(map.get(key1), s"'$key1' not found")
  b <- optionEither(map.get(key2), s"'$key2' not found")
} yield a + b

(addKeys("key1", "key2").runEither.run, addKeys("key1", "missing").runEither.run)

def testKeys(keys: List[String]): Eff[S, List[Int]] = for {
  result <- allKeys(keys)
} yield result


def allKeys(keys: List[String]) = {
  val result= for {
    key <- keys
    result = optionEither(map.get(key), s"'$key' not found")
  } yield result

  implicitly[Traverse[List]].sequence(result)
}

testKeys(List("key1", "missing", "key2")).runEither.run


def testKeys2(keys: List[String]): Eff[S, List[Int]] = for {
  result <- allKeys2(keys)
} yield result

def allKeys2(keys: List[String]) = {
  val result= for {
    key <- keys
    result = fromEither(map.get(key).toRight("Error"))
  } yield result

  implicitly[Traverse[List]].sequence(result)
}

val e1: Either = testKeys2(List("key1", "missing", "key2")).runEither.run
e1.catchL

package example.eff

import org.atnos.eff.all._
import org.atnos.eff.syntax.all._

object EffDemo2 extends App {

  // the type parameter A can be read as the type of value returned by the operation
  sealed trait KVStore[+A]

  case class Put[T](key: String, value: T) extends KVStore[Unit]
  case class Get[T](key: String) extends KVStore[Option[T]]
  case class Delete(key: String) extends KVStore[Unit]

  import org.atnos.eff._

  // T |= R is an alias for MemberIn[T, R]
// stating that effects of type T[_] can be injected in the effect stack R
// It is also equivalent to MemberIn[KVStore, R]
  type HasKVStore[R] = MemberIn[KVStore, R]

  /** store returns nothing (i.e. Unit) */
  def store[T, R :HasKVStore](key: String, value: T): Eff[R, Unit] =
    Eff.send[KVStore, R, Unit](Put(key, value))

  /** find returns a T value if the key exists */
  def find[T, R :HasKVStore](key: String): Eff[R, Option[T]] =
    Eff.send[KVStore, R, Option[T]](Get(key))

  /** delete returns nothing (i.e. Unit) */
  def delete[T, R :HasKVStore](key: String): Eff[R, Unit] =
    Eff.send(Delete(key))

  /** update composes get and put, and returns nothing. */
  def update[T, R :HasKVStore](key: String, f: T => T): Eff[R, Unit] =
    for {
      ot <- find[T, R](key)
      _  <- ot.map(t => store[T, R](key, f(t))).getOrElse(Eff.pure(()))
    } yield ()

  def program[R :HasKVStore]: Eff[R, Option[Int]] =
    for {
      _ <- store("wild-cats", 2)
      _ <- update[Int, R]("wild-cats", _ + 12)
      _ <- store("tame-cats", 5)
      n <- find[Int, R]("wild-cats")
      _ <- delete("tame-cats")
    } yield n

  import org.atnos.eff._, interpret._
  import cats.Traverse
  import cats.implicits._
  import scala.collection.mutable._


  /**
    * Unsafe interpreter for KVStore effects
    *
    * the program will crash if a type is incorrectly specified.
    *
    * The interpreter requires the KVStore effect to be a Member of R (with <=)
    * Meaning that we can statically know the resulting type once we have removed
    * KVStore from R, and this type is m.Out.
    *
    * The interpreter uses the `interpretUnsafe` method from `org.atnos.eff.Interpreter` to implement a
    * stack-safe interpretation of effects as a side-effect.
    *
    * `interpretUnsafe` needs the definition of a side-effect where
    * we get each `KVStore[X]` effect, run side-effects and return a value `X`.
    *
    * The resulting effect stack is m.Out which is R without the KVStore effects
    *
    */
  def runKVStoreUnsafe[R, A](effects: Eff[R, A])(implicit m: KVStore <= R): Eff[m.Out, A] = {
    // a very simple (and imprecise) key-value store
    val kvs = Map.empty[String, Any]

    val sideEffect = new SideEffect[KVStore] {
      def apply[X](kv: KVStore[X]): X =
        kv match {
          case Put(key, value) =>
            println(s"put($key, $value)")
            kvs.put(key, value)
            ().asInstanceOf[X]

          case Get(key) =>
            println(s"get($key)")
            kvs.get(key).asInstanceOf[X]

          case Delete(key) =>
            println(s"delete($key)")
            kvs.remove(key)
            ().asInstanceOf[X]
        }

      def applicative[X, Tr[_] : Traverse](ms: Tr[KVStore[X]]): Tr[X] =
        ms.map(apply)
    }
    interpretUnsafe(effects)(sideEffect)(m)

  }

  println(program[Fx.fx1[KVStore]])

// run the program with the unsafe interpreter
  runKVStoreUnsafe(program[Fx.fx1[KVStore]]).run

}

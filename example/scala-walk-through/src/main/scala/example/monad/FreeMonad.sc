// ADT representing the operation
sealed trait KVStoreA[A]
case class Put[T](key: String, value: T) extends KVStoreA[Unit]
case class Get[T](key: String) extends KVStoreA[Option[T]]
case class Delete(key: String) extends KVStoreA[Unit]

// 1. Free the above ADT
import cats.free.Free

type KVStore[A] = Free[KVStoreA, A]

// 2. Create smart constructors using liftF
import cats.free.Free.liftF

// Put returns nothing (i.e. Unit).
def put[T](key: String, value: T): KVStore[Unit] =
  liftF[KVStoreA, Unit](Put[T](key, value))

// Get returns a T value.
def get[T](key: String): KVStore[Option[T]] =
  liftF[KVStoreA, Option[T]](Get[T](key))

// Delete returns nothing (i.e. Unit).
def delete(key: String): KVStore[Unit] =
  liftF(Delete(key))

// Update composes get and set, and returns nothing.
def update[T](key: String, f: T => T): KVStore[Unit] =
  for {
    vMaybe <- get[T](key)
    _ <- vMaybe.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
  } yield ()

// 3. Build a program
def program: KVStore[Option[Int]] =
  for {
    _ <- put("cats", 2)
    _ <- update[Int]("cats", (_ + 12))
    n <- get[Int]("cats")
    _ <- delete("cats")
  } yield n

// 4. Write the interpreter
import cats.{Id, ~>}
import scala.collection.mutable

def impureCompiler: KVStoreA ~> Id  =
  new (KVStoreA ~> Id) {

    // a very simple (and imprecise) key-value store
    val kvs = mutable.Map.empty[String, Any]

    def apply[A](fa: KVStoreA[A]): Id[A] =
      fa match {
        case Put(key, value) =>
          println(s"put($key, $value)")
          kvs(key) = value
          ()
        case Get(key) =>
          println(s"get($key)")
          kvs.get(key).map(_.asInstanceOf[A])
        case Delete(key) =>
          println(s"delete($key)")
          kvs.remove(key)
          ()
      }
  }

// 5. Run our program
// Free[_] is just a recursive structure, which similar to List.
val result: Option[Int] = program.foldMap(impureCompiler)


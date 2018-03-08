import cats.{Id, Monad}
import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap

val a = Monad[Id].pure(3)
// a: cats.Id[Int] = 3
val b = Monad[Id].flatMap(a)(_ + 1)
// b: cats.Id[Int] = 4

for {
  x <- a
  y <- b
} yield x + y
// res6: cats.Id[Int] = 7

a.flatMap(x => {
  b.map(y => x+y)
})


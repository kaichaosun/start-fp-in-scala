# Monad


## 常用的Monad

### Id

```
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
```
### Either

### Eval Manad
Eval有三个子类型，Now、Later和Always。
```
import cats.Eval

val now = Eval.now(1)
val later = Eval.later(2)
val always = Eval.always(3)
```
通过`value`方法进行取值。

Eval和Scala lazy的比较：
| scala | cats | properties |
|---|---|---|
| val | Now | eager, memoized |
| lazy val | Later | lazy, memoized |
| def | Always | lazy, not memoized |

### Writer


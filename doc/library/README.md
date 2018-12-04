# Common used libraries

# Cats

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

### State

### Custom Monad
通过实现flatMap, pure, tailRecM为一个自定义的类型提供Monad。

## Monad transformer

Cats为很多Monad提供了transformer，以T结尾，如：EitherT是Either和其他Monad组合，OptionT组合Option和其他Monad。

## Validated
* map, leftMap, bimap
* toEither
* withEither
* ensure


# Http4s

HTTP applications are jsut a Kleisli function from a streaming request to a polymorphic effect of a streaming reponse.

https://rossabaker.github.io/boston-http4s/

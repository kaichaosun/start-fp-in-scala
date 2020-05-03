# Container type

Data type like `List[A]`, `Option[A]`, `Either[A, B]` etc, can be viewed as a container of basic type, which means it can store one or many value along with some effect. *TODO: explain what's effect*. The `A` , `B` in square brackets is generic type identifier. We will explain more about generic type later.  A another confusive name for container type is called [Higher Kinded Type](https://en.m.wikipedia.org/wiki/Kind_(type_theory)). For a long time, you don't need to care about that name.

#### Option

`Option` is used to replace the null in OOPL (Object Oriented Programming Language). If there is scenario that the function can not return a valid data, then you should probably use `Option`.

Here we have defined a function called `divide`, if the divident is 0, then the underlying divide operation `/` will throw a exception called `ArithmeticException`. Option is perfect to be used under this scenario. With `None` represents the error scenario, and `Some`  wrap the actual value.

Both `None` and `Some(value)` are just the data type that extends `Option`.  

```scala
def divide(m: Int, n: Int): Option[Int] =
  if (n == 0) None
  else Some(m / n)
```

Scala standanrd library has provided a lot of functions to make Dev's life much easier when dealing with `Option`. I strongly suggest you do following exercises in amm REPL.

```scala
Option.empty == None // true

Option(10) == Some(10) // true

Some(10).get // 10

None.get // NoSuchElementException

None.getOrElse(0) // 0

Some(10).fold(0)(_ + 2) // 12

None.fold(0)((x: Int) => x + 2) // 0
```

#### Either



#### Try



#### Tuple



#### List

```
scala> List(1,2,3)
res0: List[Int] = List(1, 2, 3)

scala> 1 :: 2 :: 3 :: Nil
res1: List[Int] = List(1, 2, 3)
```

#### Set

```
scala> Set(1, 1, 2)
res2: scala.collection.immutable.Set[Int] = Set(1, 2)
```

#### Seq

```
scala> Seq(1,1,2)
res3: Seq[Int] = List(1, 1, 2)
```

#### Map

```
scala> Map('a'->1, 'b'->2)
res4: res1: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, b -> 2) 
```

#### Stream:  

TODO
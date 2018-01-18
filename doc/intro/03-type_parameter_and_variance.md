# Scala类型系统：Parameterized Types 和 Variances

## Why variance
Scala中集合是covariance，我们可以使用子类集合替换父类集合。例如，List[Circle]可以用于任意需要一个List[Shape]的地方，因为Circle是Shape的子类。

Contravariance, 当构造代表一些操作的类型时，十分有用。如JsonWriter type class:
```
trait Json
trait JsonWriter[-A] {
  def write(value: A): Json
}

val shape: Shape = ???
val circle: Circle = ???

val shapeWriter: JsonWriter[Shape] = ???
val circleWriter: JsonWriter[Circle] = ???

def format[A](value: A, writer: JsonWriter[A]): Json =
  writer.write(value)
```
JsonWriter[Shape]是JsonWriter[Circle]的子类，也就是所，任何使用shapeWriter的地方可以用circleWriter替换。
那么我们的circle就可以和任意一个writer结合使用。


## References
[1] https://blog.codecentric.de/en/2015/03/scala-type-system-parameterized-types-variances-part-1/



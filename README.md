# A tutorial for Functional programming in Scala

# Setup

## 安装Java 8 JDK
打开terminal，检查版本
```
java -version
```
如果没有安装，请在[这里下载](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## 安装sbt, 0.13.13或更高版本
Mac OSX 使用brew进行安装:
```
brew install sbt
```

Linux 安装:
TODO

## Hello world in Scala 
sbt 0.13.13 增加了`new`命令，可以根据模块创建Scala项目。
```
sbt new sbt/scala-seed.g8
```
根据提示信息，输入你的项目名，完成项目的创建:
```
A minimal Scala project.

name [Scala Seed Project]: scala-walk-through

Template applied in ./scala-walk-through
```

现在我们已经有了一个最基本的项目，查看目录结构：
```
ls -l scala-walk-through
total 8
-rw-r--r--  1 kcsun  staff  282 Jan  3 12:00 build.sbt
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 project
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 src

它们的职责如下：
```
* `build.sbt`, 项目的构建文件，包含了项目的基本信息。
* `project`, 本目录中包含了项目构建过程所依赖的第三方插件等。
* `src`, 代码包括测试代码的源文件目录。sbt代码管理风格和Maven一致。

修改源代码，将`src/main/scala/example/Hello.scala`，其中一行修改为:
```
lazy val greeting: String = "Hello, Functional Programming!"
```

通过sbt运行程序有两种方式，直接通过`sbt run`运行；或者执行`sbt`进入交互REPL模式，再执行`run`。输出：
```
Hello, Functional Programming!
```

Ctrl + d 或输入`exit`退出sbt。

# Basics in Scala

## REPL
REPL (Read-Eval-Print Loop)是编程语言的交互执行环境，可以通过下面两种方式进入Scala的REPL：
* sbt console
* amm
其中amm是第三方REPL工具，支持语法高亮、代码复制等用户友好的操作。通过如下命令进行安装：
```
sudo curl -L -o /usr/local/bin/amm https://git.io/vdNv2 && sudo chmod +x /usr/local/bin/amm && amm
```

## Scala basics
本文包含一些基本的语法和概念，如常量、变量、表达式、函数等。可以通过REPL进行练习，增加对语法的熟悉。

### val
通过val, 可以将表达式的结果赋值给一个常量，常量值不能改变。
```
scala> val three = 1 + 2
two: Int = 3
``` 

### var
var定义的值为变量，可被修改。
```
scala> var name = "dasheng"
name: String = dasheng

scala> name = "kaichao"
name: String = kaichao
```

### expressions
Scala中几乎所有的语句都是表达式，就连`if else`、`patter match`也都是表达式。
expressions 总是返回结果，几乎不产生任何side effect；相反，statements 并不返回任何结果，仅仅为了获取side effect而执行。
EOP（expression-oriented programming）是函数式编程语言的共有特点，更多内容请参考[这里](https://alvinalexander.com/scala/best-practice-think-expression-oriented-programming-eop)。
通过EOP，代码变得：
* easy to reason about
* easy to test
* excute in parallel

```
scala> if (true) 1 else 0
res0: Int = 1

scala> "hp" match {
     |   case "hp" => "computer"
     |   case _ => "non-computer"
     | }
res1: String = computer
```

### functions

[Functions vs Methods](http://jim-mcbeath.blogspot.com/2009/05/scala-functions-vs-methods.html)

### common data structure
**List:**
```
scala> List(1,2,3)
res0: List[Int] = List(1, 2, 3)

scala> 1 :: 2 :: 3 :: Nil
res1: List[Int] = List(1, 2, 3)
```
**Set:**
```
scala> Set(1, 1, 2)
res2: scala.collection.immutable.Set[Int] = Set(1, 2)
```
**Seq:**
```
scala> Seq(1,1,2)
res3: Seq[Int] = List(1, 1, 2)
```
**Map:**
```
scala> Map('a'->1, 'b'->2)
res4: res1: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, b -> 2) 
```

**Stream:**

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

# Shapeless
Function1:
```scala
val f: A => B = ???
```

Natural Transformation:
```scala
val nat: F ~> G
or 
val nat: F[A] => G[A]
```


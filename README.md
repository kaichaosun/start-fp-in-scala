**A tutorial for Functional programming in Scala**

This book is aimed to be the first place when you starting functional programming in Scala. Feedback and PR are welcome.

* [Setup](#setup)
   * [Install Java 8 JDK](#install-java-8-jdk)
   * [Install sbt](#install-sbt)
   * [Hello world with sbt](#hello-world-with-sbt)
   * [REPL](#repl)
   * [IDE](#ide)
* [Basic Knowledge in Scala](#basic-knowledge-in-scala)
   * [Basic type](#basic-type)
   * [Container type](#container-type)
      * [Option](#option)
      * [Either](#either)
      * [Try](#try)
      * [Tuple](#tuple)
      * [List](#list)
      * [Set](#set)
      * [Seq](#seq)
      * [Map](#map)
      * [Stream:  TODO](#stream--todo)
   * [Variable](#variable)
   * [Expression](#expression)
   * [Conditional Control](#conditional-control)
   * [Loop Control](#loop-control)
   * [Function](#function)
   * [Traits](#traits)
   * [Class &amp; Objects](#class--objects)
   * [Comment](#comment)
* [ELI5 - Category Theory](#eli5---category-theory)
   * [What's Category Theory](#whats-category-theory)
   * [Object and Arrow](#object-and-arrow)
   * [Terminal Object](#terminal-object)
   * [Product Type](#product-type)
   * [Sum Type](#sum-type)
   * [Kleisili Arrow](#kleisili-arrow)
   * [Monoid](#monoid)
   * [Functor](#functor)
   * [Monad](#monad)
* [Daily Libraries](#daily-libraries)
   * [cats](#cats)
      * [Id](#id)
      * [Either](#either-1)
      * [Eval](#eval)
      * [Writer](#writer)
      * [State](#state)
      * [Customized Monad](#customized-monad)
      * [Monad transformer](#monad-transformer)
      * [Validated](#validated)
   * [cats-effect](#cats-effect)
   * [fs2](#fs2)
   * [circe](#circe)
   * [http4s](#http4s)
   * [doobies](#doobies)
   * [monix](#monix)
   * [specs2](#specs2)
   * [shapeless](#shapeless)
* [Common Functionality](#common-functionality)
   * [File Read / Write](#file-read--write)
   * [Common Calculation](#common-calculation)
   * [Http Request](#http-request)
* [Use Cases](#use-cases)
   * [Scala Web Server](#scala-web-server)
   * [Big Data Process](#big-data-process)
   * [Web Crawler](#web-crawler)
   * [Machine Learning](#machine-learning)
   * [ScalaJS](#scalajs)
* [Other Knowledge](#other-knowledge)
   * [Scala Type System: Parameterized Types and Variances](#scala-type-system-parameterized-types-and-variances)
      * [Why variance](#why-variance)
* [References](#references)

## Functional Programming Introduction

### Whats FP

As always, there is a tedious definition for functional programming in [wikipedia](https://en.wikipedia.org/wiki/Functional_programming). A more practical explaination is in [Why OO Sucks](http://www.cs.otago.ac.nz/staffpriv/ok/Joe-Hates-OO.htm) by Joe Armstrong which pointed out the wroing design in objected orientated programming. I highly recommend you read this article. 

Then it's time to summary whats fp in your own word. It hasn't to be one hundred percent right, just lets yourself think deeper when writing something.

I will share my understanding. In functional prgramming, program strongly depends on immutable variable and pure function to make the program easy to reason. There is nothing secrets in immutable variables and pure function.

* Immutable variable: variables that can't be changed after instantiated
* Pure function: the function takes inputs, then do computation, finally return a value. The inputs is all the funtion needs to do computation, the returned value is the only way that the function can change the program "world".

That's fp, a enjoyable programming paradigm. 

But why it's so hard to learn for new comers.

From my learning experience, a few pain points are:

* Too many unintuitive termnology like functor, monad, etc. Actually you can start enjoy the convinence that FP brings without understanding all these words.
* Reading a lot of book, but practice too late. As you use more features in Scala, you will find more beauty in FP.

So in this book, I encourage you to practice as much as you can, it doesn't need to be formal, REPL expression is always a good start.

### Why FP

There are lots of people knows OO, but really less people understand FP. Once someone marries with FP, they never want to go back. It just makes me feel happy and right. A few phenomenon since I wrote code in FP with Scala:

* Haven't debug for a really long time.
* The code is readable, nearly no comments.
* Easily mock in unit test.
* Less bug.
* Makes me love to be a developer.

## Setup

### Install Java 8 JDK
Open your favorite terminal, check installed Java version:
```shell
java -version
```
If it's not installed, download [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Install sbt
For Mac OS users: 
```shell
brew install sbt
```

For Arch Linux users:

```shell
yay -S sbt
```

### Hello world with sbt 
Using sbt new command to create an simple project:
```shell
sbt new sbt/scala-seed.g8
```
Follow the prompt information, Input the project name to finish the setup:
```shell
A minimal Scala project.

name [Scala Seed Project]: scala-walk-through

Template applied in ./scala-walk-through
```

Now we have a basic project, check the directories structure:
```shell
ls -l scala-walk-through
total 8
-rw-r--r--  1 kcsun  staff  282 Jan  3 12:00 build.sbt
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 project
drwxr-xr-x  4 kcsun  staff  136 Jan  3 12:00 src
```
Let me explain the files:

* `build.sbt` - the project configuration file, it includes basic information for the project like project name, group name, scala version, dependencies, etc. 
* `project` - this folder contains the file
  * `build.properties` used to set sbt version
  * `plugins.sbt` used to define all the available plugins for the build
  * Other project settings you want to extract from the `build.sbt` to make the main file clean and tight.
* `src` - this folder maintains all the source code and test code. sbt has the same code structure style with Maven.  

Modify the source code in`src/main/scala/example/Hello.scala` to be:
```scala
lazy val greeting: String = "Hello, Functional Programming!"
```

Run your newest program with sbt command, you can use `sbt run` directly, or get into interactive mode with `sbt`, then execute `run`. You will get following output if you are lucky:

```shell
Hello, Functional Programming!
```

Exit sbt with ctrl + d or simply input `exit`。

### REPL
REPL (Read-Eval-Print Loop) is one of the most useful environments if you want to try a new programming language. There is a few options for Scala:
* sbt console - help us import the dependencies in the project, can be used directly in this way.

* amm - most efficient tool developed by [lihaoyi](https://github.com/lihaoyi), it has user friendly features like syntax highlight, multiple line input, etc. Install with following command:

  ```shell
  sudo curl -L -o /usr/local/bin/amm https://git.io/vdNv2 && sudo chmod +x /usr/local/bin/amm && amm
  ```

### IDE

[IntelliJ IDEA](https://www.jetbrains.com/idea/) - There is a free community version for idea. 

Open the IntelliJ IDEA, 

* go to **File -> New -> Project from existing sources**, choose the root folder of your project and open.
* select **sbt** under `Import project from external model`.
* select **Library sources** and choose the proper JDK version, fow now I use JDK 1.8.
* click **finish**, now after downloading all the dependencies, you should be able to get your project up. 

## Basic Knowledge in Scala

This section includes some basic concepts in Scala, like basic type (String, Int), data type (List, Option, Either), 

Most of code listed in this book can be run directly in REPL tools like amm.

### Basic type

Scala provides **nine** predefined basic or scalar type, they are:

* Boolean: `true`, `false`
* Int: `1`
* String: `"hello"`
* Unit: `()`
* Char: `c`
* Long: `100L`
* Short: `100: Short`
* Float: `31.4f`
* Double: `3.14d`

### Container type

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

#### Stream:  TODO

### Variable
通过val, 可以将表达式的结果赋值给一个常量，常量值不能改变。
```
scala> val three = 1 + 2
two: Int = 3
```

var定义的值为变量，可被修改。
```
scala> var name = "dasheng"
name: String = dasheng

scala> name = "kaichao"
name: String = kaichao
```

### Expression
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

### Conditional Control



### Loop Control



### Function

[Functions vs Methods](http://jim-mcbeath.blogspot.com/2009/05/scala-functions-vs-methods.html)

### Traits



### Class & Objects



### Comment



## ELI5 - Category Theory

### What's Category Theory



### Object and Arrow



### Terminal Object



### Product Type



### Sum Type



### Kleisili Arrow



### Monoid



### Functor



### Monad

## Daily Libraries

### cats

常用的Monad

#### Id

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

#### Either

#### Eval

Eval有三个子类型，Now、Later和Always。

```
import cats.Eval

val now = Eval.now(1)
val later = Eval.later(2)
val always = Eval.always(3)
```

通过`value`方法进行取值。

Eval和Scala lazy的比较：

| scala    | cats   | properties         |
| -------- | ------ | ------------------ |
| val      | Now    | eager, memoized    |
| lazy val | Later  | lazy, memoized     |
| def      | Always | lazy, not memoized |

#### Writer

#### State

#### Customized Monad

通过实现flatMap, pure, tailRecM为一个自定义的类型提供Monad。

#### Monad transformer

Cats为很多Monad提供了transformer，以T结尾，如：EitherT是Either和其他Monad组合，OptionT组合Option和其他Monad。

#### Validated

- map, leftMap, bimap
- toEither
- withEither
- ensure

### cats-effect



### fs2



### circe

[Circe](https://circe.github.io/circe/) is a library which helps handle JSON format data. You can easily decode a JSON HTTP response to a case class. If you are working on API service, probably you will use it to encode your model with JSON format, then return to the API consumer.

#### Docode

Now, let's decode an http response, JSON data looks like:

```json
{
  "userId": 1,
  "postId": 1,
  "title": "Start FP in Scala",
  "body": "This book means to be the first place when you try to learn Scala."
}
```

The `Post` model:

```scala
case class Post(userId: Int, postId: Int, title: String, body: String)
```

The decoder need to be implicit as required by circe. It needs to implements the `Docoder` trait with `apply` function implemented. 

The input `HCursor` parameter for `apply` function is used to track the operation history of go to the field. 

`Decoder.Reuslt[A]` is a type alias of `Either[DecodingFailure, A]`

Let's see decoder:

```scala
implicit object PostDecoder extends Decoder[Post] {
  override def apply(c: HCursor): Decoder.Result[Post] =
    for {
      userId <- c.get[Int]("userId")
      postId <- c.get[Int]("postId")
      title <- c.get[String]("title")
      body <- c.get[String]("body")
    } yield Post(userId, postId, title, body)
}
```

Use the above decoder to decode the input string:

```scala
io.circe.parser.decode(postString)

// result is:
// Right(Post(1, 1, "Start FP in Scala", "This book means to be the first place when you try to learn Scala."))
```

#### Encode

Imagine  you already have the a `Post` instance, you want to present it through API in beautiful JSON format.

The encoder also needs to be implicit. Import the necessary circle syntax with `import io.circe.syntax._`.

```scala
implicit object PostEncoder extends Encoder[Post] {
    override def apply(a: Post): Json =
      Json.obj(
        "userId" -> a.userId.asJson,
        "postId" -> a.postId.asJson,
        "title" -> a.title.asJson,
        "content" -> a.body.asJson,
      )
  }
```

Check the ecoded result:

```scala
val post = Post(1, 1, "Start FP in Scala", "This book means to be the first place when you try to learn Scala.")

post.asJson.noSpaces

// result is:
// {"userId":1,"postId":1,"title":"Start FP in Scala","content":"This book means to be the first place when you try to learn Scala."}
```

Easy and cool, right?

Please write the decoder and encoder for the nested JSON data.

### http4s

When you want to start a HTTP server, or a HTTP client to send request to get data from certain API, http4s could make your life much easies. It's designed to be pure functional and light weight. You will see the `IO` in cats-effect mentions a lot during play with http4s.

#### Server

Before you writing more code, you should bare in mind that this library heavily depends on cats. So do not forget to import `cats.implicits._` if you meet any weird issue. Also, the library is still in fast involvement, please use this latest version. The code below uses `0.20.0`.

```scala
// Construct an HttpRoutes
val helloHttpRoute = HttpRoutes.of[IO] {
  case GET -> Root / "hello" / name =>
  	Ok(s"hello $name from http4s")
}

// Build an HttpApp which is alias for Kleisli (wrap for function: A => F[B])
val httpApp = Router(
  "/" -> helloHttpRoute
).orNotFound

// Start up a server
BlazeServerBuilder[IO]
  .bindHttp(8080, "0.0.0.0")
  .withHttpApp(httpApp)
  .serve
  .compile
  .drain
  .as(ExitCode.Success)
```

`GET` `Root` `Ok` keyword are all from dsl provided by http4s, it defined in ` org.http4s.dsl.io._`.

`BlazeServerBuilder` is used to setup a server, and it uses fs2 `stream` as a stream processing container.

Check the [full souce code](https://github.com/dashengSun/start-fp-in-scala/blob/master/example/scala-walk-through/src/main/scala/example/http4s/HttpServer.scala).

#### Client

HTTP client is almost the most important part when writing programs, especially microservice are so famous in recent years. http4s has provided us a very useful toolkit to do such things.

You need to add the dependency in build.sbt:

```scala
"org.http4s" %% "http4s-blaze-server" % Http4sVersion,
```

```scala
import scala.concurrent.ExecutionContext.global

// Build a client resource
val resource = BlazeClientBuilder[IO](global).resource

// This is the request body
val body =
  """
    |{
    |    "title":"start fp in scala",
    |    "body":"The start scala book",
    |    "userId":1
    |}
  """.stripMargin

// Construct the request with HTTP method, endpoint and body stream
val request = Request[IO](
  method = Method.POST,
  uri = Uri.unsafeFromString("https://jsonplaceholder.typicode.com")
    .withPath("/posts"),
  body = Stream.emits[IO, Byte](body.getBytes)
)

// Parse the response
val result = resource.use(_.fetch(request){
  case Status.Successful(r) => r.as[String].flatMap(body => IO{ println(body) })
  case r: Any => r.as[String].flatMap(err => IO.raiseError(new Exception(err)))
})
```

The `BlazeClientBuild` needs an implicit `ExecutionContext`.

The body string can be encoded from a model with circe's help.

You can also use `r.as[A]` `r.attempAs[A]` to get the model you want to decoded. Here we use string for simplicity.

Check the [source code](https://github.com/dashengSun/start-fp-in-scala/blob/master/example/scala-walk-through/src/main/scala/example/http4s/HttpClient.scala)

### doobie



### monix



### specs2



### shapeless
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

## Common Functionality

This section may not necessary.

### File Read / Write



### Common Calculation



### Http Request



## Use Cases

### API Server

* Setup a empty project with sbt.

  Resource: https://www.scala-sbt.org/1.0/docs/sbt-new-and-Templates.html

* Add http4s library and create a test API with response hello world.

  Resource: https://http4s.org/v0.18/service/

* Add model `Post(Int userId, Int id, String title, String body)` and create API to response a single Get post request as String. 

  Resource: https://docs.scala-lang.org/tour/case-classes.html

* Add circe library and make the previous response as JSON, example:

  ```json
  {
    "userId": 1,
    "postId": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
  }
  ```

  Resource: https://circe.github.io/circe/codecs/custom-codecs.html

* Add library `http4s-blaze-client`, and call the endpoint to get post information `https://jsonplaceholder.typicode.com/posts/1`

  Resource: https://http4s.org/v0.18/client/

* Decode the previous response to `Post` model and return as API response.

  Resource: https://circe.github.io/circe/codecs/custom-codecs.html

### Big Data Process



### Web Crawler



### Machine Learning

TODO



### ScalaJS



## Other Knowledge

### Scala Type System: Parameterized Types and Variances

#### Why variance
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

## License

[![License: CC BY-NC-ND 4.0](https://licensebuttons.net/l/by-nc-nd/4.0/80x15.png)](https://creativecommons.org/licenses/by-nc-nd/4.0/)

This book is licensed under the [CC BY-NC-ND 4.0](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.en). Please [contact me](mailto:kaichaosuna@gmail.com) if you have questions about the license.




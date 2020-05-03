# http4s

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
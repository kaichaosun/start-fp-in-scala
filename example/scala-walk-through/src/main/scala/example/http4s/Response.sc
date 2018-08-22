import cats.effect._
// import cats.effect._

import org.http4s._
// import org.http4s._

import org.http4s.dsl.io._
// import org.http4s.dsl.io._

import org.http4s.server.blaze._
// import org.http4s.server.blaze._

val service = HttpService[IO] {
  case GET -> Root / "hello" / name =>
    Ok(s"Hello, $name.")
}
// service: org.http4s.HttpService[cats.effect.IO] = Kleisli(org.http4s.HttpService$$$Lambda$43335/2075132568@6d035c09)

val builder = BlazeBuilder[IO].bindHttp(8085, "localhost").mountService(service, "/").start

val server = builder.unsafeRunSync


import org.http4s.client.blaze._
// import org.http4s.client.blaze._

val httpClient = Http1Client[IO]().unsafeRunSync

val helloJames = httpClient.expect[String]("http://localhost:8080/hello/James")

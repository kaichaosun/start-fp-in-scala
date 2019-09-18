//package example.http4s
//
//import cats.effect.{ExitCode, IO, IOApp}
//import cats.implicits._
//import org.http4s.HttpRoutes
//import org.http4s.dsl.io._
//import org.http4s.implicits._
//import org.http4s.server.Router
//import org.http4s.server.blaze.BlazeServerBuilder
//
//object HttpServer extends IOApp {
//  override def run(args: List[String]): IO[ExitCode] = {
//    val helloHttpRoute = HttpRoutes.of[IO] {
//      case GET -> Root / "hello" / name =>
//        Ok(s"hello $name from http4s")
//    }
//
//    val httpApp = Router(
//      "/" -> helloHttpRoute
//    ).orNotFound
//
//    BlazeServerBuilder[IO]
//      .bindHttp(8080, "0.0.0.0")
//      .withHttpApp(httpApp)
//      .serve
//      .compile
//      .drain
//      .as(ExitCode.Success)
//  }
//}

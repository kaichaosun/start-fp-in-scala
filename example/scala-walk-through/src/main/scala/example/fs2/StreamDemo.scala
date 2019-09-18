//package example.fs2
//import cats.effect.{ExitCode, IO, IOApp}
//import fs2.Stream
//
//import scala.concurrent.duration._
//
//object StreamDemo extends IOApp {
//  override def run(args: List[String]): IO[ExitCode] = {
//    val s = for {
//      env <- Stream.eval(IO(sys.env))
//      _ <- Stream.eval(IO(println(s"begin ${env}")))
//      _ <- Stream.fixedDelay[IO](3.second)
//      _ <- Stream.eval(IO(println("hello repeat")))
//    } yield ()
//
//    s.compile.drain.flatMap(_ => IO(ExitCode.Success))
//  }
//}

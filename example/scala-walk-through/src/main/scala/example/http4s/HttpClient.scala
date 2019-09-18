//package example.http4s
//import cats.effect.{ExitCode, IO, IOApp}
//import org.http4s.{Method, Request, Status, Uri}
//import org.http4s.client.blaze.BlazeClientBuilder
//import fs2.Stream
//import cats.implicits._
//
//object HttpClient extends IOApp {
//  override def run(args: List[String]): IO[ExitCode] = {
//    import scala.concurrent.ExecutionContext.global
//
//    val resource = BlazeClientBuilder[IO](global).resource
//
//    val body =
//      """
//        |{
//        |    "title":"start fp in scala",
//        |    "body":"The start scala book",
//        |    "userId":1
//        |}
//      """.stripMargin
//
//    val request = Request[IO](
//      method = Method.POST,
//      uri = Uri.unsafeFromString("https://jsonplaceholder.typicode.com")
//        .withPath("/posts"),
//      body = Stream.emits[IO, Byte](body.getBytes)
//    )
//
//    val result = resource.use(_.fetch(request){
//      case Status.Successful(r) => r.as[String].flatMap(body => IO{ println(body) })
//      case r: Any => r.as[String].flatMap(err => IO.raiseError(new Exception(err)))
//    })
//
//    result *> IO.pure(ExitCode.Success)
//  }
//}

package example.http4s

import cats.effect.IO
import org.http4s.client.blaze._
import org.http4s.{Method, Request, Uri}

object Client {
  def main(args: Array[String]): Unit = {

    val httpClient = Http1Client[IO]()

    val request = Request[IO](
      method = Method.POST,
      uri = Uri.unsafeFromString("http://localhost:8084/longresponse")
    )

    val response = httpClient.flatMap(_.open.run(request)).unsafeRunSync().response

    println("=============")
    println(response.as[String].unsafeRunSync())
    println("===================================================")
    println(response.as[String].unsafeRunSync())
  }
}

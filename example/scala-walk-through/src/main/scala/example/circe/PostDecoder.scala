package example.circe

import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.parser.decode

object PostDecodeMain extends App {
  val postString =
    """
      |{
      |  "userId": 1,
      |  "postId": 1,
      |  "title": "Start FP in Scala",
      |  "body": "This book means to be the first place when you try to learn Scala."
      |}
    """.stripMargin

  case class Post(userId: Int, postId: Int, title: String, body: String)

  implicit object PostDecoder extends Decoder[Post] {
    override def apply(c: HCursor): Decoder.Result[Post] =
      for {
        userId <- c.get[Int]("userId")
        postId <- c.get[Int]("postId")
        title <- c.get[String]("title")
        body <- c.get[String]("body")
      } yield Post(userId, postId, title, body)
  }

  import io.circe.syntax._

  implicit object PostEncoder extends Encoder[Post] {
    override def apply(a: Post): Json =
      Json.obj(
        "userId" -> a.userId.asJson,
        "postId" -> a.postId.asJson,
        "title" -> a.title.asJson,
        "content" -> a.body.asJson,
      )
  }

  val post = decode(postString)
  println(s"Decode result: ${post}")

  val encodedPost = post.right.get.asJson.noSpaces
  println(s"Encode result: $encodedPost")

  decode(postString) == Right(Post(1, 1, "Start FP in Scala", "This book means to be the first place when you try to learn Scala."))

}




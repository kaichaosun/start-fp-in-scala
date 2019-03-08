package example.circe

object CirceListDecoder extends App {
  import io.circe.{Decoder, HCursor}
  val input =
    """
      |{
      |    "a":1,
      |    "e":{"b":[
      |        {
      |            "c":3,
      |            "d":4
      |        }
      |    ]}
      |}
    """.stripMargin.trim

  case class TestModel(c: Int, d: Int)

  implicit val testDecoder = new Decoder[TestModel] {
    override def apply(c: HCursor): Decoder.Result[TestModel] = {
      for {
        tc <- c.get[Int]("c")
        td <- c.get[Int]("d")
      } yield TestModel(tc, td)

    }
  }

  implicit val allDecoder = new Decoder[List[TestModel]] {
    override def apply(c: HCursor) =
      for {
        result <- c.downField("e").get[List[TestModel]]("b")
      } yield result
  }

  val result = io.circe.parser.decode(input)(allDecoder)
  println(result)
}

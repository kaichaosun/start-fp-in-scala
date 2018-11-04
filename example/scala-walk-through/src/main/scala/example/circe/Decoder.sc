import io.circe.{ Decoder, Encoder, HCursor, Json }
import io.circe.syntax._
import io.circe.parser

case class Thing(foo: String, bar: Int)

implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
  final def apply(a: Thing): Json = Json.obj(
    ("Auto-upgrade: Pricing and Eligibility at activation date?", Json.fromString(a.foo)),
    ("bar", Json.fromInt(a.bar))
  )
}

implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {
  final def apply(c: HCursor): Decoder.Result[Thing] =
    for {
      foo <- c.downField("Auto-upgrade: Pricing and Eligibility at activation date?").as[String]
      bar <- c.downField("bar").as[Int]
    } yield Thing(foo, bar)
}

val t = new Thing("test",2).asJson

parser.decode[Thing](t.noSpaces)
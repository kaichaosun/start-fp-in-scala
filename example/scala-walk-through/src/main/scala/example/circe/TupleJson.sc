import io.circe.{Encoder, Json}
import io.circe.generic.auto._
import io.circe.syntax._

val a = ("test", 3)
a.asJson

implicit val tupleEncoder: Encoder[Tuple2[String, Int]] = new Encoder[Tuple2[String, Int]] {
  override def apply(a: Tuple2[String, Int]): Json = Json.obj(
    "a" -> a._1.asJson,
    "b" -> a._2.asJson
  )
}
import io.circe.generic.auto._

import io.circe.{Encoder, Json}
import io.circe.syntax._

sealed abstract class SubscriptionItem(
  val title: String,
  val description: String,
  val termsOfUse: Option[String]
)

object SubscriptionItem {
  implicit object SubscriptionItemEncoder extends Encoder[SubscriptionItem] {
    override def apply(item: SubscriptionItem): Json =
      Json.obj(
        "title" -> item.title.asJson,
        "description" -> item.description.asJson,
        "termsOfUse" -> item.termsOfUse.asJson
      )
  }
}

case class ExceptionContract(
  override val description: String,
  override val termsOfUse: Option[String]
) extends SubscriptionItem(title = "Exception", description = description, termsOfUse = Some("test special term"))




val f = ExceptionContract("hello", Some("hehe"))
val f2 = ExceptionContract("h2", None)
f.asJson
val lb: List[SubscriptionItem] = List(f)
lb.asJson

val lb2: List[SubscriptionItem] = List(f,f2)
lb2.asJson

val lb3: List[Option[SubscriptionItem]] = List(Some(f),None)
lb3.asJson
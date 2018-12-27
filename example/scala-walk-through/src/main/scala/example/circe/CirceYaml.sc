import cats.syntax.either._
import io.circe._
import io.circe.generic.auto._
import io.circe.yaml

case class Nested(one: String, two: BigDecimal)
case class Foo(foo: String, bar: Nested, baz: List[String])

val json = yaml.parser.parse(
  """
foo: Hello, World
bar:
    one: One Third
    two: 33.333333
baz:
    - Hello
    - World
""")

val foo = json
  .leftMap(err => err: Error)
  .flatMap(_.as[Foo])
  .valueOr(throw _)

val configString =
  """
    |product1:
    |  - "key1": hello
    |    "key2": 1
    |    "key3": true
    |
    |product2:
    |  - "key1": hello
    |    "key2": 1
    |    "key3": true
    |    "key4": 1
    |    "key5": false
    |
    |  - "key1": hello
    |    "key2": 1
    |    "key3": false
    |    "key5": false
  """.stripMargin

val configJson = yaml.parser.parse(configString)

case class Config(
    key1: Option[String],
    key2: Option[Int],
    key3: Option[Boolean],
    key4: Option[Int],
    key5: Boolean = false
)

configJson.map(x => x.\\("premiere_all_reupgrade_all_buy"))


implicit val configDecoder = new Decoder[Config] {
  override def apply(c: HCursor): Decoder.Result[Config] = for {
    reupgradePastProduct <- c.get[Option[String]]("key1")
    reupgradeMinimumPastProductUpgrades <- c.get[Option[Int]]("key2")
    isAutoUpgrade <- c.get[Option[Boolean]]("key3")
    reupgradeMaximumPastUpgrades <- c.get[Option[Int]]("key4")
    applicableToUnderOffer <- c.getOrElse("key5")(false)
  } yield Config(
    reupgradePastProduct,
    reupgradeMinimumPastProductUpgrades,
    isAutoUpgrade,
    reupgradeMaximumPastUpgrades,
    applicableToUnderOffer
  )
}

configJson.flatMap(_.as[Map[String, List[Config]]])




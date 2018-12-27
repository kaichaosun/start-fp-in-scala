import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import scala.concurrent.ExecutionContext

val program1 = 42.pure[ConnectionIO]

implicit val cs = IO.contextShift(ExecutionContext.global)

val xa = Transactor.fromDriverManager[IO](
  "org.postgresql.Driver",
  "jdbc:postgresql:host",
  "",
  ""
)

val io = program1.transact(xa)

io.unsafeRunSync()

val program2 = sql"select 42".query[Int].unique

val io2 = program2.transact(xa)

io2.unsafeRunSync

val program3: ConnectionIO[(Int, Double)] =
  for {
    a <- sql"select 42".query[Int].unique
    b <- sql"select random()".query[Double].unique
  } yield (a, b)

program3.transact(xa).unsafeRunSync

// ======================== select ===========================
val selectProgram = sql"select name from country"
  .query[String]
  .to[List]
  .transact(xa)
  .unsafeRunSync

val selectProgram2 = sql"select code, name, population, gnp from country"
  .query[(String, String, Int, Option[Double])]
  .stream
  .take(5)
  .compile
  .toList
  .transact(xa)
  .unsafeRunSync




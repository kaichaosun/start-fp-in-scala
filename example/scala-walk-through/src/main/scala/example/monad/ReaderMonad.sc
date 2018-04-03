import cats.data.Reader
import cats.implicits._

case class Cat(name: String, favoriteFood: String)

val catName: Reader[Cat, String] =
  Reader(cat => cat.name)

catName.run(Cat("Mimi", "lasagne"))

val greetKitty: Reader[Cat, String] =
  catName.map(name => s"hello ${name}")

greetKitty.run(Cat("Mimi", "fish"))

val feedKitty: Reader[Cat, String] =
  Reader(cat => s"Having a nice bowl of ${cat.favoriteFood}")

val greetAndFeed: Reader[Cat, String] =
  for {
    greet <- greetKitty
    feed <- feedKitty
  } yield s"$greet. $feed."

greetAndFeed(Cat("mao", "rice"))

// exercise
case class Db(
  usernames: Map[Int, String],
  passwords: Map[String, String]
)

type DbReader[A] = Reader[Db, A]

def findUsername(userId: Int): DbReader[Option[String]] =
  Reader(db => db.usernames.get(userId))

def checkPassword(username: String, password: String): DbReader[Boolean] = {
  Reader(db => {
    db.passwords.get(username).contains(password)
  })
}


def checkLogin(userId: Int, password: String): DbReader[Boolean] = {
  for {
    usernameOp <- findUsername(userId)
    passwordOk <- usernameOp.map { username =>
                    checkPassword(username, password)
                  }.getOrElse { false.pure[DbReader] }
  } yield passwordOk
}

val users = Map(
  1 -> "dade",
  2 -> "kate",
  3 -> "margo"
)

val passwords = Map(
  "dade" -> "zerocool",
  "kate" -> "acidburn",
  "margo" -> "secret"
)

val db = Db(users, passwords)

checkLogin(1, "zerocool").run(db)
checkLogin(4, "davinci").run(db)
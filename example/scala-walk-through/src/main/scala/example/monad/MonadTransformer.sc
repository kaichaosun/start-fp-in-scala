import cats.{Applicative, Functor, Monad}
import cats.data.OptionT

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._

case class Address(address: String)
case class User(id: Long)

def findUserById(id: Long): Future[Option[User]] = Future.successful(Some(User(id)))
def findAddressByUser(user: User): Future[Option[Address]] = Future.successful(Some(Address("Xi'an")))

def findAddressByUserId(id: Long): Future[Option[Address]] =
  findUserById(id).flatMap {
    case Some(user) => findAddressByUser(user)
    case None       => Future.successful(None)
  }

val result = findAddressByUserId(1)

Await.result(result, 1 second)

// User OptionT
def findAddressByUserIdOptionT(id: Long): OptionT[Future, Address] =
  for {
    user <- OptionT(findUserById(id))
    address <- OptionT(findAddressByUser(user))
  } yield address

val result2 = findAddressByUserIdOptionT(1)

Await.result(result, 1 second)


// OptionT implementation
case class TestOptionT[F[_], A](value: F[Option[A]]) {
  def map[B](f: A => B)(implicit F: Functor[F]): TestOptionT[F, B] =
    TestOptionT(value.map(_.map(f)))

  def flatMap2[B](f: A => TestOptionT[F, B])(implicit F: Monad[F]): TestOptionT[F, B] =
    TestOptionT(F.flatMap(value)(_.fold(F.pure[Option[B]](None))(a => f(a).value)))

  def flatMap[B](f: A => TestOptionT[F, B])(implicit F: Monad[F]): TestOptionT[F, B] =
    TestOptionT(value.flatMap(
      _.fold(Option.empty[B].pure[F])(
        f andThen ((fa: TestOptionT[F, B]) => fa.value)))
    )
}
object TestOptionT {
  def pure[F[_]: Applicative, A](a: A): TestOptionT[F, A] =
    TestOptionT(a.pure[Option].pure[F])
}




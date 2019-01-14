import cats.Monad
import cats.data.EitherT
import cats.instances.future._

import scala.collection.mutable
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global


case class User(id: Long, name: String, age: Int)

class DatabaseError extends Throwable
case object ErrorFindingUser extends DatabaseError
case object ErrorUpdatingUser extends DatabaseError
case class ErrorDeletingUser(msg: String) extends DatabaseError

trait ConsoleAlgebra[F[_]] {
  def putLine[T](t: T): F[Unit]
}

object ConsoleAlgebra {
  implicit object FutureInterpreter extends ConsoleAlgebra[Future] {
    override def putLine[T](t: T): Future[Unit] = Future.successful{
      println(t)
    }
  }
}

trait DatabaseAlgebra[F[_], T] {
  def create(t: T): F[Boolean]
  def read(id: Long): F[Either[DatabaseError, T]]
  def delete(id: Long): F[Either[DatabaseError, Unit]]
}

object DatabaseAlgebra {

  val FutureInterpreter: DatabaseAlgebra[Future, User] =
    new DatabaseAlgebra[Future, User] {
      val users: mutable.Map[Long, User] = mutable.Map.empty

      override def create(user: User): Future[Boolean] = {
        val inserted = users.put(user.id, user)
        Future.successful(inserted.isEmpty || inserted.isDefined)
      }

      override def read(id: Long): Future[Either[DatabaseError, User]] =
        Future.successful(users.get(id).toRight(ErrorFindingUser))

      override def delete(id: Long): Future[Either[DatabaseError, Unit]] = {
        import cats.syntax.either._
        val deleted = users.remove(id)
        Future.successful(
          deleted.fold(ErrorDeletingUser(s"User with Id($id) was not there").asLeft[Unit])(_ => Right(())))
      }
    }

}


class UserRepo[F[_]](DB: DatabaseAlgebra[F, User],
    C: ConsoleAlgebra[F])
  (implicit M: Monad[F]) {

  def getUser(id: Long): F[Either[DatabaseError, User]] = DB.read(id)
  def addUser(user: User): F[Boolean] = DB.create(user)

  def updateUser(user: User): F[Either[DatabaseError, Boolean]] = {
    (for {
      userFromDB <- EitherT(getUser(user.id))
      _ <- EitherT.liftF(C.putLine(s"We found user($userFromDB)!!"))
      successfullyAdded <- EitherT.liftF[F, DatabaseError, Boolean](addUser(user))
    } yield successfullyAdded).value
  }

}

object UserRepoRunner extends App {
  val repo = new UserRepo(DatabaseAlgebra.FutureInterpreter, ConsoleAlgebra.FutureInterpreter)

  println(Await.result(
    (for {
      _ <- repo.addUser(User(1, "Bob", 31))
      dbErrorOrSuccessfullyUpdated <- repo.updateUser(User(1, "Bobby", 31))
    } yield dbErrorOrSuccessfullyUpdated),
    1 second))

}
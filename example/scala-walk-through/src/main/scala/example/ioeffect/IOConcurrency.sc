import java.util.concurrent.Executors
import cats.effect.{ContextShift, Fiber, IO}
import cats.syntax.apply._
import scala.concurrent.ExecutionContext

val ecOne = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
val ecTwo = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())

val csOne: ContextShift[IO] = IO.contextShift(ecOne)
val csTwo: ContextShift[IO] = IO.contextShift(ecTwo)

def infiniteIO(id: Int)(implicit cs: ContextShift[IO]): IO[Fiber[IO, Unit]] = {
  def repeat: IO[Unit] = IO(println(id)).flatMap(_ => IO.shift *> repeat)

  repeat.start
}

val prog =
  for {
    _ <- infiniteIO(1)(csOne)
    _ <- infiniteIO(11)(csOne)
    _ <- infiniteIO(2)(csTwo)
    _ <- infiniteIO(22)(csTwo)
  } yield ()

prog.unsafeRunSync()
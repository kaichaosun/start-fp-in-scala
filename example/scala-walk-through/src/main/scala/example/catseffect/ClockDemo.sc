import cats.effect._
import cats.implicits._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.MILLISECONDS


def measure[F[_], A](fa: F[A])
  (implicit F: Sync[F], clock: Clock[F]): F[(A, Long)] = {

  for {
    start  <- clock.monotonic(MILLISECONDS)
    result <- fa
    finish <- clock.monotonic(MILLISECONDS)
  } yield (result, finish - start)
}

implicit def executionContext =
  ExecutionContext.global
implicit val timer: Timer[IO] =
  IO.timer(executionContext)

val time = System.currentTimeMillis()
val iotime = timer.clock.monotonic(MILLISECONDS)
iotime.unsafeRunSync

iotime.unsafeRunSync()

for (t <- iotime.unsafeToFuture()) yield {
  println(t)
}

measure(IO.delay(println("hello clock"))).unsafeRunSync


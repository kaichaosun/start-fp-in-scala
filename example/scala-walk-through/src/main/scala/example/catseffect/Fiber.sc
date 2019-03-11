// Fiber

import cats.effect.{Fiber, IO}
import cats.implicits._

import scala.concurrent.ExecutionContext.global

implicit val ctx = IO.contextShift(global)

val io = IO(println("Hello!"))

val fiber: IO[Fiber[IO, Unit]] = io.start


// Fiber

import cats.effect.{Fiber, IO}
import cats.implicits._

import scala.concurrent.ExecutionContext.global

implicit val ctx = IO.contextShift(global)

val io = IO(println("Hello!"))

val fiber: IO[Fiber[IO, Unit]] = io.start

val launchMissiles = IO.raiseError(new Exception("boom!"))
val runToBunker = IO(println("To the bunker!!!"))

val program = for {
  fiber <- launchMissiles.start
  _ <- runToBunker.handleErrorWith { error =>
    // Retreat failed, cancel launch (maybe we should
    // have retreated to our bunker before the launch?)
    fiber.cancel *> IO.raiseError(error)
  }
  aftermath <- fiber.join
} yield aftermath

program.unsafeRunSync


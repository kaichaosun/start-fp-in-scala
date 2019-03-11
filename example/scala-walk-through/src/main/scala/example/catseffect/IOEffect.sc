import cats.effect.IO

val io1 = IO { println("hello IO") }

val program: IO[Unit] =
  for {
    _ <- io1
    _ <- io1
  } yield ()

program.unsafeRunSync()

val boom = IO.raiseError(new Exception("boom"))

//boom.unsafeRunSync()

boom.attempt.unsafeRunSync()
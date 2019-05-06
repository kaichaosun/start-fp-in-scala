import cats.effect.IO
import cats.implicits._

val program = IO{ println("hello") }.whenA(false)

program.unsafeRunSync()
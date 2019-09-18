import cats.effect.IO
import fs2.Stream

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


//Stream(1,2,3).repeat.take(9).toList

implicit val timer = IO.timer(ExecutionContext.global)

val s = for {
  env <- Stream.eval(IO(sys.env))
  _ <- Stream.eval(IO(println(s"begin ${env}")))
  _ <- Stream.fixedDelay[IO](3000.second)
  _ <- Stream.eval(IO(println("hello repeat")))
} yield ()

s.compile.drain.unsafeRunSync()

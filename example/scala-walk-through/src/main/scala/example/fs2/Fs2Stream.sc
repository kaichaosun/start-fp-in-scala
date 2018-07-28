import cats.effect.IO
import fs2.Stream

Stream.emit(1)


Stream.eval_(IO[Byte] { "hehe".toByte })
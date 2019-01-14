import java.time.Instant

import fs2.Chunk
import fs2.Stream.chunk
import io.circe.syntax._
import io.circe.generic.auto._

case class CirceDemo(
    ei: String,
    ct: Instant,
    et: String = "QT",
    re: String = "EA",
    cs: String = "rdp"
)

val demo = CirceDemo("test", Instant.now())

val bytes = demo.asJson.noSpaces.getBytes

val stream1 = chunk(Chunk.array(bytes))

stream1.take(10000).compile.toVector.toArray

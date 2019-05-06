import fs2.Chunk
import fs2.Stream

val s1c = Stream.chunk(Chunk.doubles(Array(1.0, 2.0, 3.0)))

s1c.mapChunks{
  chunk =>
    val doubles = chunk.toDoubles
    println(doubles.values.size)
    doubles
}
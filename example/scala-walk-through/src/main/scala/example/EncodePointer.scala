package example

import java.time.OffsetDateTime

object EncodePointer extends App {

  val modifiedAt: OffsetDateTime = OffsetDateTime.parse("2017-03-22T00:10:00Z")

  val lid = 12345

  val encodedTime = modifiedAt.toEpochSecond

  val nanos = modifiedAt.getNano

  val encoded = f"${encodedTime}%012X-${nanos}%08X-${lid}%012X".toLowerCase

  println(s"Encoded of ${modifiedAt} and ${lid}: ${encoded}")
}

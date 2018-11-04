import java.time.Instant

object Test extends App {
  implicit class TestImplicit(v: String) {
    def parseTime: Instant =
      Instant.parse(v)
  }


  println("2017-09-01T14:00:00Z".parseTime)
}


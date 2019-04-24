package example.cats

import cats.Apply
import org.specs2.matcher.Scope
import org.specs2.mutable.Specification
import cats.implicits._

class ApplySpec extends Specification {
  "Apply" should {
    "should have map" in new Scope {
      Apply[List].map(List(1, 2, 3))(_ + 1) should_== (List(2, 3, 4))
    }
  }
}

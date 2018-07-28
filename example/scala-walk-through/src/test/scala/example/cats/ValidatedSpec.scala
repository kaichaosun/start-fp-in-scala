package example.cats

import example.validated.{Email1, Form1, Password1}
import org.specs2.mutable.Specification
import org.specs2.matcher.Scope

class ValidatedSpec extends Specification {
  "say hi" should {
    "return hehe" in new Scope {
      "hehe" should_== "hehe"
    }
  }

  "Using Either" should {
    "there is no errors" should {
      "return right" in new Scope {
        val form = Form1(Email1("test@test.com"), Password1("password"))
        form.verify should beRight(((),()))
      }
    }

    "there is no errors" should {
      "return left" in new Scope {
        val form = Form1(Email1("test@test.com"), Password1("pass"))
        form.verify should beLeft("Password is weak")
      }
    }
  }

  "Using Validate" should {
    "there is no errors" should {
      "return success" in new Scope {

      }
    }

    "there is multiple errors" should {
      "return error list" in new Scope {

      }
    }
  }

}

package example.validated

import cats.data.Validated
import Validated.{Invalid, Valid, invalid, invalidNel, valid}
import cats.data.ValidatedNel
import cats.implicits._

object Main extends App {

  //type Error = List[String]
  //type ValidatedR = Validated[Error, Int]
  //
  //val v1: ValidatedR = valid(1)
  //val v2: ValidatedR = valid(2)
  //val v3: ValidatedR = valid(3)
  //
  //val result = (v1 |@| v2 |@| v3) map { (_,_,_) }
  //
  //result match {
  //  case Valid(x) => x
  //  case Invalid(x) => 10
  //}


  case class User(password: String, email: String)

  sealed trait UserCreationError
  case object PasswordTooShort  extends UserCreationError
  case object EmailWrongPattern extends UserCreationError

  val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".r

  def checkEmailPattern(email: String): ValidatedNel[UserCreationError, String] = (emailPattern findFirstIn email) match {
    case Some(_) => valid(email)
    case None    => invalidNel(EmailWrongPattern)
  }

  def checkPasswordLength(password: String): ValidatedNel[UserCreationError, String] =
    if (password.length < 6)
      invalidNel(PasswordTooShort)
    else
      valid(password)


  //def createUser(password: String, email: String): ValidatedNel[UserCreationError, User] =
  //  (checkPasswordLength(password) |@| checkEmailPattern(email)) map {(_,_)}

  //createUser("123456", "kaichaosuna@gmail.com")

  val password = "123456"
  val email = "email@gmail.com"
  val result = (checkPasswordLength(password), checkEmailPattern(email)).mapN((_,_))

//  result: Validated

  println(result)
}

package example.validated

sealed trait InputField1 {
  def verify: Either[String, Unit]
}

case class Email1(email: String) extends InputField1 {
  val regrex = """^[a-zA-Z0-9\.-_]+@[a-zA-Z0-9]+\.[A-Za-z]{2,6}""".r
  override def verify: Either[String, Unit] =
    regrex.findFirstIn(email) match {
      case None => Left("Email is not valid")
      case Some(_) => Right()
    }

}

case class Password1(password: String) extends InputField1 {
  override def verify: Either[String, Unit] =
    if (password.length < 6) {
      Left("Password is weak")
    } else {
      Right()
    }
}

case class Form1(email: Email1, password: Password1) {
  def verify: Either[String, (Unit, Unit)] = {
    for {
      x <- email.verify
      y <- password.verify
    } yield (x, y)
  }
}
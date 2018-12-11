package example.akka.demodb.server.messages

case class KeyNotFoundException(key: String) extends Exception

case class UndefinedRequestException(o: Any) extends Exception

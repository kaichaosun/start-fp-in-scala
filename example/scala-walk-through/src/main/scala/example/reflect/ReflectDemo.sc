import scala.reflect.runtime.{universe => ru}
import ru._

def getProperties[T: TypeTag]: Iterable[String] = {
  val tpe = ru.typeTag[T].tpe
  tpe.decls.collect {
    case m: MethodSymbol if m.isCaseAccessor => m.name.toString
  }
}

case class User(name: String, age: Int) {
  val other: Long = 0
}

getProperties[User]

val user = User("kc", 20)
"name"

import scala.annotation.{implicitNotFound}

@implicitNotFound(msg = "Cannot find Serializable type class for ${T}")
trait Serializable[T]

def foo[X : Serializable](x : X) = x

implicit val serialInt = new Serializable[Int]{}
implicit val serialString = new Serializable[String]{}

println(foo(5))
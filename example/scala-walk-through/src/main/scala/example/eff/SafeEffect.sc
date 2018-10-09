import org.atnos.eff.syntax.all._
import org.atnos.eff._, all._

// let's represent a resource which can be in use
case class Resource(values: List[Int] = (1 to 10).toList, inUse: Boolean = false) {
  def isClosed = !inUse
}

var resource = Resource()

// our stack of effects, with safe evaluation
type S = Fx.fx1[Safe]

def openResource: Eff[S, Resource] =
  protect { resource = resource.copy(inUse = true); resource }

def closeResource(r: Resource): Eff[S, Unit] =
  protect(resource = r.copy(inUse = false))

def useResource(ok: Boolean) = (r: Resource) =>
  protect[S, Int](if (ok) r.values.sum else throw new Exception("boo"))

// this program uses the resource safely even if there is an exception
def program(ok: Boolean): (Throwable Either Int, List[Throwable]) =
  bracket(openResource)(useResource(ok))(closeResource).
    runSafe.run



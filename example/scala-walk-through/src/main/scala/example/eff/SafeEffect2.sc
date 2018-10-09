import org.atnos.eff.syntax.all._
import org.atnos.eff._, all._

// our stack of effects, with safe evaluation
type S = Fx.fx1[Safe]

var sumDone: Boolean = false

def setDone(ok: Boolean): Eff[S, Unit] =
  protect[S, Unit](if (ok) sumDone = true else throw new Exception("failed!!"))

// this program tries to set sumDone to true when the computation is done
def program(ok: Boolean, finalizeOk: Boolean): (Throwable Either Int, List[Throwable]) =
  (protect[S, Int](if (ok) (1 to 10).sum else throw new Exception("boo")) `finally` setDone(finalizeOk)).
    runSafe.run

program(false, true)
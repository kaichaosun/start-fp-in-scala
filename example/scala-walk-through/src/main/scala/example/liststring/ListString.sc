val sl = List("Buy", "Rural", "Other", "Land")

val slso = sl.sorted

val splitList = List(slso.slice(0, slso.length - 1), slso.slice(slso.length - 1, slso.length))

splitList.map(l => l.mkString(", ")).filter(!_.isEmpty).mkString(" and ")


case class Test(a: String)

object TestInstances {
  implicit object TestOrdering extends Ordering[Test] {
    override def compare(x: Test, y: Test): Int =
      if (x.a.length > y.a.length) 1 else -1
  }
}

import TestInstances.TestOrdering
List(Test("hello"), Test("world"), Test("test"), Test("thisistest")).sorted

//List(Test("hello"), Test("world"), Test("test"), Test("thisistest")).sortBy

abstract class Dep(val prio: Int) extends Ordered[Dep] {
  override def compare(that: Dep): Int = prio.compare(that.prio)
}
//
//implicit object DepOrdering extends Ordering[Dep]{
//  override def compare(the: Dep, that: Dep): Int = the.prio.compare(that.prio)
//}

case object P extends Dep(3)
case object H extends Dep(2)
case object F extends Dep(1)

val listDep: List[Dep] = List(F, P, H)
listDep.sorted.reverse

case class WrapDepth(depth: Dep)

implicit object WrapDepthOrdering extends Ordering[WrapDepth] {
  override def compare(x: WrapDepth, y: WrapDepth): Int =
    x.depth.compare(y.depth)
}

List(WrapDepth(F), WrapDepth(P), WrapDepth(H)).sorted

P > F

F > H






import cats.implicits._

List(Option(1),None, Option(2), Option(3)).sequence

//implicitly
case class Test(a:Int, b:String)
val t1 = Test(1, "f1")
val t2 = Test(2, "f2")

List(t1,t2).filter(_.a>3).groupBy(_.a).map{case (k,v)=> Option(k+1)}.toList
List(t1,t2).filter(_.a>3).groupBy(_.a).map{case (k,v)=> Option(k+1)}.toList.sequence

List(t1,t2).filter(_.a>3).groupBy(_.a).map{case (k,v)=> None}.toList
List(t1,t2).filter(_.a>3).groupBy(_.a).map{case (k,v)=> None}.toList.sequence


List(t1,t2).filter(_.a>1).groupBy(_.a).map{case (k,v)=> Option(k+1)}.toList
List(t1,t2).filter(_.a>1).groupBy(_.a).map{case (k,v)=> Option(k+1)}.toList.sequence

List(t1,t2).filter(_.a>1).groupBy(_.a).map{case (k,v)=> None}.toList
List(t1,t2).filter(_.a>1).groupBy(_.a).map{case (k,v)=> None}.toList.sequence
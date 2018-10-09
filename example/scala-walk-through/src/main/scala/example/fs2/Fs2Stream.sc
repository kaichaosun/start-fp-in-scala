import cats.effect.IO
import fs2.Stream

Stream.emit(1)


Stream.eval_(IO[Byte] { "hehe".toByte })

val s1 = Stream.emit(List(1,2,3))

s1.toList

(Stream(1,2,3) ++ Stream(4,5)).toList

Stream(1,2,3).map(_ + 1).toList

Stream("hehe", 1).toList

Stream(1,2,3).filter(_ % 2 != 0).toList

Stream(None,Some(2),Some(3)).collect { case Some(i) => i }.toList

Stream.range(0,5).intersperse(42).toList

Stream(1,2,3).flatMap(i => Stream(i,i)).toList

Stream(1,2,3).repeat.take(9).toList

val eff = Stream.eval(IO {println("hello fs2"); 1+1})

// Any Stream formed using eval is called ‘effectful’
// and can’t be run using toList or toVector

eff.compile.toVector.unsafeRunSync() // gather all output into a Vector

eff.compile.drain.unsafeRunSync() // purely for effects

eff.compile.fold(2)(_ + _).unsafeRunSync() // run and accumulate result

import fs2.Chunk
val chunk = Stream.chunk(Chunk.doubles(Array(1.0,2.0,3.0)))

//chunk.mapChunks{ ds =>
//  val doubles = ds.toDoubles
//  doubles.values.size
//  doubles
//}
//

import cats.implicits._

val err = Stream.raiseError[IO[_]](new Exception("oh noes!"))

import fs2._
// import fs2._

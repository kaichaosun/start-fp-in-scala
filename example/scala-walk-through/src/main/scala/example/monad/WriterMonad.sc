import cats.data.Writer
import cats.instances.vector._

Writer(Vector(
  "It was the best of times",
  "It was the worst of times"
), 1859)


import cats.syntax.applicative._ // for pure

type Logged[A] = Writer[Vector[String], A]

123.pure[Logged]

import cats.syntax.writer._ // for tell

Vector("msg1", "msg2", "msg3").tell

val a = Writer(Vector("msg1", "msg2"), 123)

123.writer(Vector("msg1", "msg2"))

val aResult: Int = a.value

val aLog: Vector[String] = a.written

val (log, result) = a.run

// composing and transforming Writer

val writer1 = for {
  a <- 10.pure[Logged]
  _ <- Vector("a", "b", "c").tell
  b <- 32.writer(Vector("x", "y", "z"))
} yield a + b

writer1.run

val writer2 = writer1.mapWritten(_.map(_.toUpperCase))

val writer3 = writer1.bimap(
  log => log.map(_.toUpperCase),
  res => res * 100
)

val writer4 = writer1.mapBoth { (log, res) =>
  val log2 = log.map(_ + "!")
  val res2 = res * 1000
  (log2, res2)
}

val writer5 = writer1.reset

val writer6 = writer1.swap
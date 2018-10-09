import cats.Functor
import io.circe.{Decoder, Encoder, HCursor, Json}
import cats.instances.list._
import cats.instances.option._

//Functor[Decoder.Result].compose(Functor[Option])



//def decode(c: HCursor): Unit =
//  for {
//    _ <- Functor[Decoder.Result].compose(Functor[Option]).map(c.get[Option[String]]("impressions"))(_)
//  } yield ()

val listOption = List(Some(1), None, Some(2))
Functor[List].compose[Option].map(listOption)(_ + 1)

def needsFunctor[F[_]: Functor, A](fa: F[A]): F[Unit] = Functor[F].map(fa)(_ => ())

def foo: List[Option[Unit]] = {
  val listOptionFunctor = Functor[List].compose[Option]
  type ListOption[A] = List[Option[A]]
  needsFunctor[ListOption, Int](listOption)(listOptionFunctor)
}

foo

import cats.data.Nested
import cats.syntax.functor._

val nested: Nested[List, Option, Int] = Nested(listOption)

nested.map(_ + 1)

import io.circe.syntax._

val list = List("timestamp" := 10, "severity" := "ERROR")
Json.fromFields(list).noSpaces
import cats.data.ValidatedNec
import cats.{Applicative, Id}

val result = Applicative[Id].compose[ValidatedNec[Throwable,?]].map2()
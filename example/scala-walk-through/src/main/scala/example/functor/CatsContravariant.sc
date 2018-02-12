import cats.Contravariant
import cats.Invariant
import cats.Show
import cats.instances.string._

val showString = Show[String]

val showSymbol = Contravariant[Show].
  contramap(showString)((sym: Symbol) => s"'${sym.name}")

showSymbol.show('Dave)

import cats.syntax.contravariant._

showString.contramap[Symbol](_.name).show('Dave)


import cats.Monoid
import cats.instances.string._
import cats.syntax.invariant._
import cats.syntax.semigroup._

implicit val symbolMonoid: Monoid[Symbol] =
  Monoid[String].imap(Symbol.apply)(_.name)

Monoid[Symbol].empty

'a |+| 'few |+| 'words

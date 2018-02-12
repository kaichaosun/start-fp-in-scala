package example.functor

import cats.Functor
import cats.syntax.functor._

sealed trait Tree[+A]

object Tree {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = {
    Branch(left, right)
  }

  def leaf[A](value: A): Tree[A] = {
    Leaf(value)
  }
}

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object Main extends App {
  implicit val treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
        tree match {
          case Branch(left, right) =>
            Branch(map(left)(f), map(right)(f))
          case Leaf(value) =>
            Leaf(f(value))
        }
    }

  println(Tree.leaf(100).map(_ * 2))
  println(Tree.branch(Leaf(100), Leaf(200)).map(_ * 2))
}
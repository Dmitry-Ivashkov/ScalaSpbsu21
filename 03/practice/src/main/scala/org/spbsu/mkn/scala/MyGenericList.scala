package org.spbsu.mkn.scala

import org.spbsu.mkn.scala.MyGenericList._


sealed trait MyGenericList[+T] {
  def head: T
  def tail: MyGenericList[T]
  def drop(n: Int): MyGenericList[T]
  def take(n: Int): MyGenericList[T]
  def map[U](f: T => U): MyGenericList[U]
  def ::[U >: T](elem: U): MyGenericList[U] = MyGenericLis(elem, this)

  def foldLeft[U](acm: U)(f: (U, T) => U): U
  def foldRight[U](acm: U)(f: (T, U) => U): U
}

object MyGenericList {
  def undef: Nothing = throw new UnsupportedOperationException("operation is undefined")

  def fromSeq[T](seq: Seq[T]): MyGenericList[T] = seq.foldRight(MyNil: MyGenericList[T])((e, t) => MyGenericLis(e, t))

  //  def sum[T](intList: MyGenericList[T]): Double = intList.foldLeft(0)(_ + _)

  def size[T](intList: MyGenericList[T]): Int = intList.foldLeft(0)((s, _) => s + 1)
}

case object MyNil extends MyGenericList[Nothing] {
  override def head: Nothing = throw new UnsupportedOperationException("MyNil . head")
  override def tail: MyGenericList[Nothing] = throw new UnsupportedOperationException("MyNil . tail")
  override def drop(n: Int): MyGenericList[Nothing] = {
    if (n == 0) return this
    throw new UnsupportedOperationException("MyNil . drop")
  }
  override def take(n: Int): MyGenericList[Nothing] = {
    if (n == 0) return this
    throw new UnsupportedOperationException("MyNil . take")
  }

  override def map[U](f: Nothing => U): MyGenericList[U] = this

  override def foldLeft[U](acm: U)(f: (U, Nothing) => U): U = acm
  override def foldRight[U](acm: U)(f: (Nothing, U) => U): U = acm
}

case class MyGenericLis[+T](override val head: T, override val tail: MyGenericList[T]) extends MyGenericList[T] {
  override def drop(n: Int): MyGenericList[T] = {
    if (n <= 0) return this
    tail.drop(n - 1)
  }

  override def take(n: Int): MyGenericList[T] = {
    if (n <= 0) return MyNil
    head :: tail.take(n - 1)
  }

  override def map[U](f: T => U): MyGenericList[U] = foldRight(MyNil: MyGenericList[U])((int, t) => f(int) :: t)

  override def foldLeft[U](acm: U)(f: (U, T) => U): U = {
    tail.foldLeft(f(acm, head))(f)
  }
  override def foldRight[U](acm: U)(f: (T, U) => U): U = {
    f(head, tail.foldRight(acm)(f))
  }
}
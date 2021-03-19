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

  def toSeq: Seq[T] = MyGenericList.toSeq(this)
}

object MyGenericList {
  def undef: Nothing = throw new UnsupportedOperationException("operation is undefined")

  def fromSeq[T](seq: Seq[T]): MyGenericList[T] = seq.foldRight(MyNil: MyGenericList[T])((e, t) => MyGenericLis(e, t))
  def toSeq[T](list: MyGenericList[T]): Seq[T] = list.foldRight(Seq[T]())((e, t) => e +: t)

  def sort[T](list: MyGenericList[T])(implicit comparator: Ordering[T]): MyGenericList[T] = fromSeq(list.toSeq.sorted(comparator))

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
//implicit class ComparatorMyGenericList: Ordering[T]
//implicit class ComparatorMyGenericList

//class F{
//  class A extends Ordering[A]{
//    val xx:Int =0
//    override def compare(x: A, y: A): Int = {
////      xx.compare(x.xx, y.xx)
//      2
//    }
//
//  }
//
//  class B[T] extends Ordering[Int]{
//    override def compare(x: Int, y: Int): Int = x-y
//  }
////  Seq(1,2,3).sorted(new (class Aa{}) )
//Seq(1,2,3).sorted(new B[Boolean])
//0 +: Seq(1, 2, 3)
//
//
//}
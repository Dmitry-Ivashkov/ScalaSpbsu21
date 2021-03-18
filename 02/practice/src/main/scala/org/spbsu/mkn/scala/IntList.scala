package org.spbsu.mkn.scala

import org.spbsu.mkn.scala.IntList._

sealed trait IntList {
  def head: Int
  def tail: IntList
  def drop(n: Int): IntList
  def take(n: Int): IntList
  def map(f: Int => Int): IntList
  def ::(elem: Int): IntList = IntLis(elem, this)

  def foldLeft[T](acm: T)(f: (T, Int) => T): T
  def foldRight[T](acm: T)(f: (Int, T) => T): T
}

object IntList {
  def undef: Nothing = throw new UnsupportedOperationException("operation is undefined")
  def fromSeq(seq: Seq[Int]): IntList = seq.foldRight(IntNil: IntList)((int, t) => IntLis(int, t))
  def sum(intList: IntList): Int = {
    intList match {
      case IntNil => throw new UnsupportedOperationException("IntNil . sum")
      case _ => intList.foldLeft(0)(_ + _)
    }
  }
  def size(intList: IntList): Int = intList.foldLeft(0)((s, _) => s + 1)
  // extra task: implement sum using foldLeft
}

case object IntNil extends IntList {
  override def head: Int = throw new UnsupportedOperationException("IntNil . head")
  override def tail: IntList = throw new UnsupportedOperationException("IntNil . tail")
  override def drop(n:  Int): IntList = {
    if (n == 0) return this
    throw new UnsupportedOperationException("IntNil . drop")
  }
  override def take(n:  Int): IntList = {
    if (n == 0) return this
    throw new UnsupportedOperationException("IntNil . take")
  }
  override def map(f:  Int => Int): IntList = this

  override def foldLeft[T](acm: T)(f: (T, Int) => T): T = acm
  override def foldRight[T](acm: T)(f: (Int, T) => T): T = acm
}

case class IntLis(override val head: Int, override val tail: IntList) extends IntList{
  override def drop(n:  Int): IntList = {
      if (n <= 0) return this
      tail.drop(n - 1)
    }
  override def take(n:  Int): IntList = {
    if (n <= 0) return IntNil
    head::tail.take(n - 1)
  }
  override def map(f:  Int => Int): IntList = foldRight(IntNil:IntList)((int, t)=>(f(int))::t)

  override def foldLeft[T](acm: T)(f: (T, Int) => T): T = {
    this match {
      case IntLis(head, tail) => tail.foldLeft(f(acm, head))(f)
    }
  }

  override def foldRight[T](acm: T)(f: (Int, T) => T): T = {
    this match {
      case IntLis(head, tail) => f (head, tail.foldRight(acm)(f))
    }
  }
}


//def drop(n: Int): IntList = {
//  if (n <= 0) return this
//  tail.drop(n - 1)
//}
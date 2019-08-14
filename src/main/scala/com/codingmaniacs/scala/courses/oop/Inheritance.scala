package com.codingmaniacs.scala.courses.oop

import scala.annotation.tailrec

object Inheritance {
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
    def isEmpty: Boolean
    def add(n: T): MyList[T]

    protected def prettyPrint: String
    override def toString: String = s"[$prettyPrint]"
  }

  class EmptyList[T] extends MyList[T] {
    override def head: T = throw new NoSuchElementException

    override def tail: MyList[T] = throw new NoSuchElementException

    override def isEmpty: Boolean = true

    override def add(n: T): MyList[T] = new CustomList(n, EmptyList())

    override protected def prettyPrint: String = ""
  }

  object EmptyList {
    def apply[T](): EmptyList[T] = new EmptyList()
  }

  class CustomList[T](x: T, xs: MyList[T]) extends MyList[T] {
    override def head: T = x

    override def tail: MyList[T] = xs

    override def isEmpty: Boolean = false

    override def add(n: T): MyList[T] = new CustomList(n, this)

    override def prettyPrint: String = {

      @tailrec
      def toStringRec(acc: String, list: MyList[T]): String =
        list match {
          case l if l.isEmpty => acc
          case l if acc.isEmpty => toStringRec(s"${l.head}", l.tail)
          case l: CustomList[T] => toStringRec(s"$acc,${l.head}", l.tail)
        }

      toStringRec("", this)
    }
  }

  object CustomList {
    def apply[T](x: T, xs: MyList[T]): MyList[T] = new CustomList(x, xs)

    def apply[T](xs: T*): MyList[T] = {

      @tailrec
      def concatRec(res: MyList[T], rem: Seq[T]): MyList[T] =
        rem match {
          case Seq() => res
          case Seq(head, tail @ _*) => concatRec(res.add(head), tail)
        }
      concatRec(EmptyList(), xs)
    }
  }

  def main(args: Array[String]): Unit = {

    val myList = CustomList[Int](1,2,3,4,5,6)

    println(myList.toString)

  }
}

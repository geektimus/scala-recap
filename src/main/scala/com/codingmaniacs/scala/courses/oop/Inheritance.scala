/*
 * Copyright (c) 2019 Geektimus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.codingmaniacs.scala.courses.oop

import scala.annotation.tailrec

object Inheritance {

  trait Predicate[-T] {
    def test(el: T): Boolean
  }

  trait Transformer[-A, B] {
    def transform(el: A): B
  }

  class DoubleTransformer extends Transformer[Int, Int] {
    override def transform(el: Int): Int = el * 2
  }

  class PairTransformer extends Transformer[Int, MyList[Int]] {
    override def transform(el: Int): MyList[Int] = new List(el, new List(el * el, EmptyList))
  }

  class EvenPredicate extends Predicate[Int] {
    override def test(el: Int): Boolean = el % 2 == 0
  }

  sealed abstract class MyList[+T] {
    def h: T
    def t: MyList[T]
    def isEmpty: Boolean

    def prepend[O >: T](n: O): MyList[O]
    def reverse: MyList[T]
    def ++[O >: T](ol: MyList[O]): MyList[O]

    def map[O](t: Transformer[T, O]): MyList[O]
    def filter(p: Predicate[T]): MyList[T]
    def flapMap[O](t: Transformer[T, MyList[O]]): MyList[O]

    protected def prettyPrint: String
    override def toString: String = s"[$prettyPrint]"
  }

  case object EmptyList extends MyList[Nothing] {
    override def h: Nothing = throw new NoSuchElementException

    override def t: MyList[Nothing] = throw new NoSuchElementException

    override def isEmpty: Boolean = true

    override def prepend[O >: Nothing](n: O): MyList[O] = new List(n, EmptyList)

    override def reverse: MyList[Nothing] = this

    override def ++[O >: Nothing](ol: MyList[O]): MyList[O] = ol

    override def map[O](t: Transformer[Nothing, O]): MyList[O] = this

    override def filter(p: Predicate[Nothing]): MyList[Nothing] = this

    override def flapMap[O](t: Transformer[Nothing, MyList[O]]): MyList[O] = this

    override protected def prettyPrint: String = ""

  }

  case class List[+T](x: T, xs: MyList[T]) extends MyList[T] {
    override def h: T = x

    override def t: MyList[T] = xs

    override def isEmpty: Boolean = false

    override def prepend[O >: T](n: O): MyList[O] = new List(n, this)

    override def reverse: MyList[T] = {

      @tailrec
      def reverseRec(res: MyList[T], rem: MyList[T]): MyList[T] =
        rem match {
          case l if l.isEmpty => res
          case l => reverseRec(res.prepend(l.h), l.t)
        }
      reverseRec(EmptyList, this)
    }

    override def ++[O >: T](ol: MyList[O]): MyList[O] =
      new List[O](h, t ++ ol)

    override def map[O](tr: Transformer[T, O]): MyList[O] = {

      @tailrec
      def mapRec(res: MyList[O], rem: MyList[T]): MyList[O] =
        rem match {
          case l if l.isEmpty => res
          case l => mapRec(res.prepend(tr.transform(l.h)), l.t)
        }
      mapRec(EmptyList, this)
    }

    override def filter(p: Predicate[T]): MyList[T] = {

      @tailrec
      def filterRec(res: MyList[T], rem: MyList[T]): MyList[T] =
        rem match {
          case l if l.isEmpty => res
          case l if p.test(l.h) => filterRec(res.prepend(l.h), l.t)
          case l => filterRec(res, l.t)
        }
      filterRec(EmptyList, this)
    }

    override def flapMap[O](tr: Transformer[T, MyList[O]]): MyList[O] =
      tr.transform(h) ++ t.flapMap(tr)

    override def prettyPrint: String = {

      @tailrec
      def toStringRec(acc: String, list: MyList[T]): String =
        list match {
          case l if l.isEmpty => acc
          case l if acc.isEmpty => toStringRec(s"${l.h}", l.t)
          case l: List[T] => toStringRec(s"$acc, ${l.h}", l.t)
        }

      toStringRec("", this)
    }
  }

  object List {
    def apply[T](x: T, xs: MyList[T]): MyList[T] = new List(x, xs)

    def apply[T](xs: T*): MyList[T] = {

      @tailrec
      def concatRec(res: MyList[T], rem: Seq[T]): MyList[T] =
        rem match {
          case Seq() => res
          case Seq(head, tail @ _*) => concatRec(res.prepend(head), tail)
        }
      concatRec(EmptyList, xs)
    }
  }
}

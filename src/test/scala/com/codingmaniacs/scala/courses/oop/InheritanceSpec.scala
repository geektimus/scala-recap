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

import com.codingmaniacs.scala.courses.oop.Inheritance.{
  DoubleTransformer,
  EmptyList,
  EvenPredicate,
  List,
  PairTransformer
}
import org.specs2.matcher.AnyMatchers
import org.specs2.mutable.Specification

class InheritanceSpec extends Specification with AnyMatchers {
  "The custom list" should {
    "allow the creation of an EmptyList" in {
      val emptyList = EmptyList
      emptyList must not be null
      emptyList.isEmpty must beTrue
    }
    "allow the user to add elements to an EmptyList" in {
      val list = EmptyList.prepend(2)
      list.isEmpty must beFalse
      list.head mustEqual 2
      list.tail.isEmpty must beTrue
    }
    "allow the user to create a non empty list" in {
      val list = new List(1, new List(2, EmptyList))
      list.isEmpty must beFalse
      list.head mustEqual 1
      val tail = list.tail
      tail.head mustEqual 2
      tail.tail.isEmpty must beTrue
    }
    "allow the user to reverse an empty list (duh!)" in {
      val emptyList = EmptyList
      emptyList.isEmpty must beTrue
      emptyList.reverse.isEmpty must beTrue
    }
    "allow the user to reverse a custom list" in {
      val list = new List(1, new List(2, EmptyList))
      list.isEmpty must beFalse
      val reversedList = list.reverse
      reversedList.head mustEqual 2
      val tail = reversedList.tail
      tail.head mustEqual 1
      tail.tail.isEmpty must beTrue
    }
    "allow the user to print the elements of a custom list" in {
      val list = new List(1, new List(2, EmptyList))
      list.isEmpty must beFalse
      list.toString mustEqual "[1,2]"
    }
    "allow create a list in a easy way" in {
      val list = List(1, 2, 3, 4, 5, 6)
      list.isEmpty must beFalse
      list.toString mustEqual "[6,5,4,3,2,1]"
    }

    "allow the user to map a transformation on a empty list" in {
      val emptyList = EmptyList
      val double = new DoubleTransformer()

      val mappedList = emptyList.map(double)

      mappedList.isEmpty must beTrue
    }

    "allow the user to map a transformation on all the elements" in {
      val list = List(1, 2, 3)
      val double = new DoubleTransformer()

      val mappedList = list.map(double)

      mappedList.isEmpty must beFalse
      mappedList.head mustEqual 2
      mappedList.tail.head mustEqual 4
      mappedList.tail.tail.head mustEqual 6
    }

    "allow the user to filter an empty list" in {
      val emptyList = EmptyList
      val evenPredicate = new EvenPredicate()

      val mappedList = emptyList.filter(evenPredicate)

      mappedList.isEmpty must beTrue
    }

    "allow the user to filter the elements of a list" in {
      val list = List(1, 2, 3)
      val evenPredicate = new EvenPredicate()

      val mappedList = list.filter(evenPredicate)

      mappedList.isEmpty must beFalse
      mappedList.head mustEqual 2
      mappedList.tail.isEmpty must beTrue
    }
  }
}

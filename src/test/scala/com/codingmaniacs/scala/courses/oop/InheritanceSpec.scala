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

import com.codingmaniacs.scala.courses.oop.Inheritance.{ EmptyList, List }
import org.specs2.matcher.AnyMatchers
import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

class InheritanceSpec extends Specification with AnyMatchers with Mockito {
  "The custom list" should {
    "allow the creation of an EmptyList" in {
      val emptyList = EmptyList
      emptyList.isEmpty must beTrue
    }
    "allow the user to add elements to an EmptyList" in {
      val list = EmptyList.prepend(2)
      list.isEmpty must beFalse
      list.h mustEqual 2
      list.t.isEmpty must beTrue
    }
    "allow the user to create a non empty list" in {
      val list = new List(1, new List(2, EmptyList))
      list.isEmpty must beFalse
      list.h mustEqual 1
      val tail = list.t
      tail.h mustEqual 2
      tail.t.isEmpty must beTrue
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
      reversedList.h mustEqual 2
      val tail = reversedList.t
      tail.h mustEqual 1
      tail.t.isEmpty must beTrue
    }
    "allow the user to print the elements of a custom list" in {
      val list = new List(1, new List(2, EmptyList))
      list.isEmpty must beFalse
      list.toString mustEqual "[1, 2]"
    }
    "allow create a list in a easy way" in {
      val list = List(1, 2, 3, 4, 5, 6)
      list.isEmpty must beFalse
      list.toString mustEqual "[6, 5, 4, 3, 2, 1]"
    }

    "allow the user to map a transformation on all the elements" in {
      val list = List(1, 2, 3)

      val mappedList = list.map(_ * 2)

      mappedList.isEmpty must beFalse
      mappedList.h mustEqual 2
      mappedList.t.h mustEqual 4
      mappedList.t.t.h mustEqual 6
    }

    "allow the user to filter the elements of a list" in {
      val list = List(1, 2, 3)

      val mappedList = list.filter(_ % 2 == 0)

      mappedList.isEmpty must beFalse
      mappedList.h mustEqual 2
      mappedList.t.isEmpty must beTrue
    }

    "allow the user to concatenate a empty list with a list" in {
      val emptyList = EmptyList
      val list = List(1, 2, 3)

      val result = emptyList ++ list

      result.isEmpty must beFalse
      result.h mustEqual 3
      result.t.h mustEqual 2
    }

    "allow the user to concatenate two lists" in {
      val list = List(1, 2)
      val otherList = List(3, 4)

      val result = otherList ++ list

      result.isEmpty must beFalse
      result.h mustEqual 4
      result.t.h mustEqual 3
      result.t.t.h mustEqual 2
    }

    "allow the user to flatMap over a list" in {
      val list = List(1, 2, 3)

      val result = list.flapMap(el => new List(el, new List(el * el, EmptyList)))

      result.isEmpty must beFalse
      result.h mustEqual 3
      result.t.h mustEqual 9
      result.toString mustEqual "[3, 9, 2, 4, 1, 1]"
    }

    "allow the user to sort a list (asc)" in {
      val list = List(3, 5, 1, 7)

      val result = list.sort((x, y) => y - x)

      result.isEmpty must beFalse
      result.h mustEqual 7
      result.t.h mustEqual 5
      result.toString mustEqual "[7, 5, 3, 1]"
    }

    "allow the user to sort a list (desc)" in {
      val list = List(3, 5, 1, 7)

      val result = list.sort((x, y) => x - y)

      result.isEmpty must beFalse
      result.h mustEqual 1
      result.t.h mustEqual 3
      result.toString mustEqual "[1, 3, 5, 7]"
    }

    "allow the user to traverse a list with foreach" in {
      val list = List(3, 5, 1, 7)

      val m = mock[Int => Unit]

      list.foreach(m(_))

      there was 4.times(m)
    }

    "allow the user to zip two lists with an operation" in {
      val list1 = List(3, 5, 1, 7)
      val list2 = List(1, 3, 5, 7)

      val result = list1.zipWith[Int, Int](list2, (x, y) => x * y)

      result.isEmpty must beFalse
      result.toString mustEqual "[3, 15, 5, 49]"
    }

    "allow the user to zip two lists of different types with an operation" in {
      val list1 = List(1, 2, 3, 4)
      val list2 = List("odd", "even", "odd", "even")

      val result = list1.zipWith[String, String](list2, (x, y) => s"$x is $y")

      result.isEmpty must beFalse
      result.toString mustEqual "[1 is odd, 2 is even, 3 is odd, 4 is even]"
    }

    "allow the user to fold the elements of a list given an initial value" in {
      val list = List(3, 5, 1, 7)

      val result = list.fold(0)((x, y) => x + y)

      result mustEqual 16
    }

    "allow the user to fold the elements of a list given an initial value != 0" in {
      val list = List(3, 5, 1, 7)

      val result = list.fold(10)((x, y) => x + y)

      result mustEqual 26
    }
  }
}

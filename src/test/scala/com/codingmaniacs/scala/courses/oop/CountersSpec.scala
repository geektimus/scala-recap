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

import com.codingmaniacs.scala.courses.oop.Counters.Counter
import org.specs2.mutable.Specification

class CountersSpec extends Specification {
  "The counter class" should {
    "allow the creation of a counter" in {
      val counter = new Counter
      counter.i mustEqual 0
    }
    "allow the user to increment a counter by 1" in {
      val counter = new Counter
      val newCounter = counter.increment.increment
      newCounter.i mustEqual 2
    }

    "allow the user to increment a counter by n" in {
      val counter = new Counter
      val newCounter = counter.increment(10)
      newCounter.i mustEqual 10
    }

    "allow the user to decrement a counter by 1" in {
      val counter = new Counter
      val newCounter = counter.increment.increment.decrement
      newCounter.i mustEqual 1
    }

    "allow the user to decrement a counter by n" in {
      val counter = new Counter
      val newCounter = counter.increment(10).decrement(5)
      newCounter.i mustEqual 5
    }
  }
}

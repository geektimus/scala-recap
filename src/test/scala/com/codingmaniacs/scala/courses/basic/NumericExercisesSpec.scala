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

package com.codingmaniacs.scala.courses.basic

import org.specs2.mutable.Specification

class NumericExercisesSpec extends Specification {
  "The calculator" should {
    "return the factorial of a given positive integer (1)" in {
      val factorial = NumericExercises.factorial(1)
      factorial.get mustEqual 1
    }
    "return the factorial of a given positive integer (5)" in {
      val factorial = NumericExercises.factorial(5)
      factorial.get mustEqual 120
    }
    "fail when provided with a non valid value (n < 1)" in {
      val factorial = NumericExercises.factorial(0)
      factorial must beNone
    }
    "return the Fibonacci of a given positive integer (1)" in {
      val fibonacci = NumericExercises.fibonacci(1)
      fibonacci mustEqual 1
    }
    "return the Fibonacci of a given positive integer (10)" in {
      val fibonacci = NumericExercises.fibonacci(10)
      fibonacci mustEqual 55
    }
  }
}

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

package com.codingmaniacs.scala.courses.exceptions

import com.codingmaniacs.scala.courses.exceptions.Exceptions.{
  MathCalculationException,
  OverflowException,
  PocketCalculator,
  UnderflowException
}
import org.specs2.mutable.Specification

class ExceptionsSpec extends Specification {
  "The pocket calculator" should {
    "add two positive numbers" in {
      val result = PocketCalculator.add(1, 2)
      result mustEqual 3
    }
    "add two negative numbers" in {
      val result = PocketCalculator.add(-5, -10)
      result mustEqual -15
    }
    "throw an OverflowException if a + b > Int.MaxValue" in {
      PocketCalculator.add(Int.MaxValue, 10) must throwAn[OverflowException]
    }
    "throw an UnderflowException if a + b < Int.MinValue" in {
      PocketCalculator.add(-10, Int.MinValue) must throwAn[UnderflowException]
    }
    "subtract two positive numbers" in {
      val result = PocketCalculator.subtract(1, 2)
      result mustEqual -1
    }
    "subtract two negative numbers" in {
      val result = PocketCalculator.subtract(-20, -30)
      result mustEqual 10
    }
    "throw an UnderflowException if a - b < Int.MinValue" in {
      PocketCalculator.subtract(-20, Int.MaxValue) must throwAn[UnderflowException]
    }
    "throw an OverflowException if a - b > Int.MaxValue" in {
      PocketCalculator.subtract(20, Int.MinValue) must throwAn[OverflowException]
    }
    "multiply two positive numbers" in {
      val result = PocketCalculator.multiply(5, 8)
      result mustEqual 40
    }
    "multiply two negative numbers" in {
      val result = PocketCalculator.multiply(-5, -8)
      result mustEqual 40
    }
    "throw an UnderflowException if a * b < Int.MinValue" in {
      PocketCalculator.subtract(-20, Int.MaxValue) must throwAn[UnderflowException]
    }
    "throw an OverflowException if a * b > Int.MaxValue" in {
      PocketCalculator.subtract(20, Int.MinValue) must throwAn[OverflowException]
    }
    "divide two numbers" in {
      val result = PocketCalculator.divide(200, 10)
      result mustEqual 20
    }
    "throw an MathCalculationException if b = 0 in a / b" in {
      PocketCalculator.divide(25, 0) must throwAn[MathCalculationException]
    }
  }
}

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

object Exceptions {

  case class OverflowException(message: String) extends RuntimeException
  case class UnderflowException(message: String) extends RuntimeException
  case class MathCalculationException(message: String) extends RuntimeException

  object PocketCalculator {

    val add: (Int, Int) => Int = (a: Int, b: Int) => {
      (a + b) match {
        case r if a > 0 && b > 0 && r < 0 =>
          throw new OverflowException("The result is bigger than Int.MaxValue")
        case r if a < 0 && b < 0 && r > 0 =>
          throw new UnderflowException("The result is smaller than Int.MinValue")
        case r => r
      }
    }

    val subtract: (Int, Int) => Int = (a: Int, b: Int) => {
      (a - b) match {
        case r if a > 0 && b < 0 && r < 0 =>
          throw new OverflowException("The result is bigger than Int.MaxValue")
        case r if a < 0 && b > 0 && r > 0 =>
          throw new UnderflowException("The result could be smaller than Int.MinValue")
        case r => r
      }
    }

    val multiply: (Int, Int) => Int = (a: Int, b: Int) => {
      (a * b) match {
        case r if a > 0 && b > 0 && r < 0 =>
          throw new OverflowException("The result is bigger than Int.MaxValue")
        case r if a > 0 && b < 0 && r > 0 =>
          throw new UnderflowException("The result is smaller than Int.MaxValue")
        case r if a < 0 && b > 0 && r > 0 =>
          throw new UnderflowException("The result is smaller than Int.MaxValue")
        case r => r
      }
    }

    val divide: (Int, Int) => Int = (a: Int, b: Int) => {
      (a, b) match {
        case (_, 0) => throw new MathCalculationException("Division by zero is not allowed")
        case (n, m) => n / m
      }
    }
  }
}

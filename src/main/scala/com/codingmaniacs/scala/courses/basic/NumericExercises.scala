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

import scala.annotation.tailrec

object NumericExercises {

  val factorial: Int => Option[Int] = (n: Int) => {

    @tailrec
    def factRec(acc: Int, number: Int): Option[Int] = number match {
      case i if i < 1 => None
      case 1 => Some(acc)
      case i => factRec(acc * i, i - 1)
    }

    factRec(1, n)
  }

  val fibonacci: Int => Int = (n: Int) => {

    @tailrec
    def fibonacciRec(i: Int, prev: Int, current: Int): Int =
      if (i <= 0) {
        current
      } else {
        fibonacciRec(i - 1, prev = prev + current, current = prev)
      }

    fibonacciRec(n, 1, 0)
  }

}

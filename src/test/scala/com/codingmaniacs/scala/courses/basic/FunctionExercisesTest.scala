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

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class FunctionExercisesTest extends AnyFunSuite with ScalaCheckDrivenPropertyChecks {

  test("The function is always curried") {
    forAll { (x: Int, y: Int) =>
      val f = (x: Int, y: Int) => x + y
      val curried = FunctionExercises.toCurry(f)
      assert(curried(x)(y) == f(x, y))
    }
  }

  test("The function is always uncurried") {
    forAll { (x: Int, y: Int) =>
      val f = (x: Int) => (y: Int) => x + y
      val uncurried = FunctionExercises.fromCurry(f)
      assert(uncurried(x, y) == f(x)(y))
    }
  }

  test("We can compose two functions using compose") {
    forAll { (a: Int) =>
      val f = (x: Int) => x * 10
      val g = (x: Int) => x / 2

      val composed = FunctionExercises.compose(f, g)
      assert(composed(a) == (f compose g)(a))
    }
  }

  test("We can compose two functions using andThen") {
    forAll { (a: Int) =>
      val f = (x: Int) => x + x
      val g = (x: Int) => x * 3

      val andThen = FunctionExercises.andThen(f, g)
      assert(andThen(a) == (g andThen f)(a))
    }
  }

}
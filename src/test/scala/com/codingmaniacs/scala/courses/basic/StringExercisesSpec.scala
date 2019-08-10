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

class StringExercisesSpec extends Specification {
  "The greeting function" should {
    "return a greeting when the name and age are provided" in {
      val greet = StringExercises.greet("User", 20)
      greet.get mustEqual "Hi, My name is User and I am 20 years old."
    }
    "fail to greet when the name is not provided" in {
      val greet = StringExercises.greet("", 20)
      greet must beNone
    }
  }

  "The string concatenator" should {
    "return a string repeated n times (4)" in {
      val repeatedStr = StringExercises.repeat("string", 4)
      repeatedStr mustEqual "stringstringstringstring"
    }
    "return a string repeated n times (2)" in {
      val repeatedStr = StringExercises.repeat("test", 2)
      repeatedStr mustEqual "testtest"
    }
    "return the given string if the number of repetitions is non valid  (n < 0)" in {
      val repeatedStr = StringExercises.repeat("nonvalid", -1)
      repeatedStr mustEqual "nonvalid"
    }
  }
}

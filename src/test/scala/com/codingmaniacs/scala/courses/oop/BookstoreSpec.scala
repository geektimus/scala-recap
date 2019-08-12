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

import com.codingmaniacs.scala.courses.oop.Bookstore.{ Novel, Writer }
import org.specs2.mutable.Specification

class BookstoreSpec extends Specification {
  "The bookstore" should {
    "allow the creation of a writer" in {
      val writer = new Writer("Edgar", "Poe", 1809)
      writer.fullname mustEqual "Edgar Poe"
    }
    "allow the creation of a novel" in {
      val novelName = "The Raven"
      val novelReleaseYear = 1845
      val allanPoe = new Writer("Edgar", "Poe", 1809)
      val novel = new Novel(novelName, novelReleaseYear, allanPoe)
      novel.name mustEqual novelName
      novel.yearOfRelease mustEqual novelReleaseYear
    }
    "allow the user to get the age of the author based on the book release date" in {
      val novelName = "The Raven"
      val novelReleaseYear = 1845
      val allanPoe = new Writer("Edgar", "Poe", 1809)
      val novel = new Novel(novelName, novelReleaseYear, allanPoe)
      novel.authorAge mustEqual 36
    }
    "allow the user to get a new revision of a book" in {
      val allanPoe = new Writer("Edgar", "Poe", 1809)
      val novel = new Novel("The Raven", 1845, allanPoe)

      val revision = novel.copy(1860)
      novel mustNotEqual revision
      novel.yearOfRelease mustNotEqual revision.yearOfRelease
      revision.authorAge mustEqual 51
    }
    "allow the user check if a book was written by a given author" in {
      val allanPoe = new Writer("Edgar", "Poe", 1809)
      val impostor = new Writer("Edgar", "Poe", 1809)
      val novel = new Novel("The Raven", 1845, allanPoe)

      val checkAuthor = novel.isWrittenBy(impostor)
      checkAuthor must beFalse
    }
  }
}

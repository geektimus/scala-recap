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

package com.codingmaniacs.scala.exercises.fs.commands

import com.codingmaniacs.scala.exercises.fs.State
import com.codingmaniacs.scala.exercises.fs.directories.Directory
import com.codingmaniacs.scala.exercises.fs.files.File

import scala.annotation.tailrec

class Echo(items: Array[String]) extends Command {

  def createContent(items: Array[String], topIndex: Int): String = {

    @tailrec
    def createContentRec(currIdx: Int, acc: String): String =
      currIdx match {
        case i if i >= topIndex => acc
        case i => createContentRec(i + 1, acc + items(currIdx))
      }

    createContentRec(0, "")
  }

  def getRootAfterEcho(
    currentDir: Directory,
    path: List[String],
    contents: String,
    append: Boolean
  ): Directory =
    if (path.isEmpty) {
      currentDir
    } else if (path.tail.isEmpty) {
      val dirEntry = currentDir.findEntry(path.head)
      if (dirEntry == null) {
        currentDir.addEntry(new File(currentDir.path, path.head, contents))
      } else if (dirEntry.isDirectory) {
        currentDir
      } else {
        if (append) {
          currentDir.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        } else {
          currentDir.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
        }
      }
    } else {
      val nextDir = currentDir.findEntry(path.head).asDirectory
      val newNextDir = getRootAfterEcho(nextDir, path.tail, contents, append)

      if (newNextDir == nextDir) {
        currentDir
      } else {
        currentDir.replaceEntry(path.head, newNextDir)
      }
    }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State =
    if (filename.contains(Directory.SEPARATOR)) {
      state.setMessage("Echo: Filename must not contain separators")
    } else {
      val newRoot: Directory = getRootAfterEcho(
        state.root,
        state.workingDir.getAllFoldersInPath :+ filename,
        contents,
        append
      )
      if (newRoot == state.root) {
        state.setMessage(s"$filename: no such file")
      } else {
        State(newRoot, newRoot.findDescendant(state.workingDir.getAllFoldersInPath))
      }
    }

  override def apply(state: State): State =
    if (items.isEmpty) {
      state
    } else if (items.size == 1) {
      state.setMessage(items(0))
    } else {
      val op = items(items.length - 2)
      val filename = items(items.length - 1)
      val contents = createContent(items, items.length - 2)
      if (">>".equals(op)) {
        doEcho(state, contents, filename, append = true)
      } else if (">".equals(op)) {
        doEcho(state, contents, filename, append = false)
      } else {
        state.setMessage(createContent(items, items.length))
      }
    }
}

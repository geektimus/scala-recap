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

class Rm(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.workingDir

    val absPath = if (name.startsWith(Directory.SEPARATOR)) {
      name
    } else if (wd.isRoot) {
      wd.path + name
    } else {
      wd.path + Directory.SEPARATOR + name
    }

    if (Directory.ROOT_PATH.equals(absPath)) {
      state.setMessage("Function not supported yet")
    } else {
      doRm(state, absPath)
    }
  }

  def doRm(state: State, path: String): State = {
    def rmHelper(currDirectory: Directory, path: List[String]): Directory =
      if (path.isEmpty) {
        currDirectory
      } else if (path.tail.isEmpty) {
        currDirectory.removeEntry(path.head)
      } else {
        val nextDir = currDirectory.findEntry(path.head)
        if (!nextDir.isDirectory) {
          currDirectory
        } else {
          val newNextDir = rmHelper(nextDir.asDirectory, path.tail)
          if (newNextDir == nextDir) {
            currDirectory
          } else {
            currDirectory.replaceEntry(path.head, newNextDir)
          }
        }
      }

    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root) {
      state.setMessage(s"$path: No such file or directory")
    } else {
      State(newRoot, newRoot.findDescendant(state.workingDir.path.substring(1)))
    }
  }
}

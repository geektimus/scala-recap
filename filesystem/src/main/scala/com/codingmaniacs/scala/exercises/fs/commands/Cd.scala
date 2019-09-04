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
import com.codingmaniacs.scala.exercises.fs.directories.{ DirEntry, Directory }

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  override def apply(state: State): State = {
    val root = state.root

    val wd = state.workingDir

    val absPath = if (dir.startsWith(Directory.SEPARATOR)) {
      dir
    } else if (wd.isRoot) {
      wd.path + dir
    } else {
      wd.path + Directory.SEPARATOR + dir
    }

    val dstDir = doFindEntry(root, absPath)

    if (dstDir == null || !dstDir.isDirectory) {
      state.setMessage(s"$dir: Not such directory")
    } else {
      State(root, dstDir.asDirectory)
    }
  }

  def doFindEntry(root: Directory, absPath: String): DirEntry = {

    @tailrec
    def findEntryHelper(currDir: Directory, path: List[String]): DirEntry =
      if (path.isEmpty || path.head.isEmpty) currDir
      else if (path.tail.isEmpty) currDir.findEntry(path.head)
      else {
        val nextDir = currDir.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }

    val tokens = absPath.substring(1).split(Directory.SEPARATOR).toList

    val newTokens = collapseRelativeTokens(tokens)

    if (newTokens == null) null else findEntryHelper(root, newTokens)
  }

  def collapseRelativeTokens(tokens: List[String]): List[String] = {
    @tailrec
    def collapseTokensRec(tokens: List[String], res: List[String]): List[String] =
      tokens match {
        case List() => res
        case h :: tail if h.equals(".") => collapseTokensRec(tail, res)
        case h :: tail if h.equals("..") =>
          res match {
            case List() => null
            case init :+ _ => collapseTokensRec(tail, init)
          }
        case h :: tail => collapseTokensRec(tail, res :+ h)
      }

    collapseTokensRec(tokens, List())
  }
}

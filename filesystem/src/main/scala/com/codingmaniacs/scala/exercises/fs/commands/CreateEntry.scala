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

abstract class CreateEntry(name: String) extends Command {

  def updateStructure(currentDir: Directory, paths: List[String], newEntry: DirEntry): Directory =
    if (paths.isEmpty) {
      currentDir.addEntry(newEntry)
    } else {
      val oldEntry = currentDir.findEntry(paths.head).asDirectory
      currentDir.replaceEntry(oldEntry.name, updateStructure(oldEntry, paths.tail, newEntry))
    }

  override def apply(state: State): State = {
    val wd = state.workingDir
    if (wd.hasEntry(name)) {
      state.setMessage(s"$name already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$name must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name: illegal entry name!")
    } else {
      doCreateEntry(state)
    }
  }

  def checkIllegal(name: String): Boolean =
    name.contains(".")

  def doCreateEntry(state: State): State = {
    val wd = state.workingDir

    val allDirsInPath = wd.getAllFoldersInPath

    val newEntry = createSpecificEntry(state)

    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)

    val newWD = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWD)

  }

  def createSpecificEntry(state: State): DirEntry
}

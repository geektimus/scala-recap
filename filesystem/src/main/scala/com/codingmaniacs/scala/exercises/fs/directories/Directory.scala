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

package com.codingmaniacs.scala.exercises.fs.directories

import com.codingmaniacs.scala.exercises.fs.files.{ File, FileSystemException }

import scala.annotation.tailrec

class Directory(
  override val parentPath: String,
  override val name: String,
  val contents: List[DirEntry]
) extends DirEntry(parentPath, name) {

  def hasEntry(name: String): Boolean = findEntry(name) != null

  def getAllFoldersInPath: List[String] =
    path.substring(1).split(Directory.SEPARATOR).toList.filter(p => !p.isEmpty)

  def findDescendant(paths: List[String]): Directory =
    if (paths.isEmpty) this
    else findEntry(paths.head).asDirectory.findDescendant(paths.tail)

  def addEntry(newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents :+ newEntry)

  def findEntry(entryName: String): DirEntry = {

    @tailrec
    def findEntryHelper(name: String, contentsList: List[DirEntry]): DirEntry =
      if (contentsList.isEmpty) {
        null
      } else if (contentsList.head.name.equals(name)) {
        contentsList.headOption.get
      } else findEntryHelper(name, contentsList.tail)
    findEntryHelper(entryName, contents)
  }

  def replaceEntry(entryName: String, newEntry: Directory): Directory =
    new Directory(parentPath, name, contents.filter(p => !p.name.equals(entryName)) :+ newEntry)

  override def asDirectory: Directory = this

  override def getType: String = "Directory"

  override def asFile: File =
    throw new FileSystemException("A folder cannot be converted to a file!")
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def empty(parentPath: String, name: String): Directory =
    new Directory(parentPath, name, List())

  def ROOT: Directory = Directory.empty("", "")
}

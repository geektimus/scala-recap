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

trait Command {
  def apply(state: State): State
}

object Command {

  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State =
      state.setMessage(s"$name is an incomplete command")
  }

  def from(input: String): Command = {
    val tokens = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) {
      emptyCommand
    } else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(MKDIR)
      } else {
        new MkDir(tokens(1))
      }
    } else if (LS.equals(tokens(0))) {
      new Ls()
    } else if (PWD.equals(tokens(0))) {
      new Pwd()
    } else if (TOUCH.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(TOUCH)
      } else {
        new Touch(tokens(1))
      }
    } else if (CD.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(CD)
      } else {
        new Cd(tokens(1))
      }
    } else if (RM.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(RM)
      } else {
        new Rm(tokens(1))
      }
    } else if (ECHO.equals(tokens(0))) {
      if (tokens.length < 2) {
        incompleteCommand(ECHO)
      } else {
        new Echo(tokens.tail)
      }
    } else {
      new UnknownCommand
    }
  }
}

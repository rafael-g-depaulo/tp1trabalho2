package command

import context.Context
import exceptions._
import types._
import value._

abstract class Command() {
  def execute(ctx: Context): Unit
  def asBlock(): Block = Block(this)
  def isReturn(): Boolean = false
  def returnValue(ctx: Context): Value[Type] = throw InvalidUseOfCommand("using non-Return command as Return command")
}
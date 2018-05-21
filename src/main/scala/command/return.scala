package command

import context.Context
import expression._
import exceptions._
import types._
import value._

class Return (
  val retVal: Expression[Type]
  ) extends Command {
  
  override def isReturn() = true

  def execute(ctx: Context) {
    throw ExecutingReturnException("função não retornou corretamente, ou return executado fora de uma função")
  }
}

object Return {
  def apply(retVal: Expression[Type]): Return = new Return(retVal)
}
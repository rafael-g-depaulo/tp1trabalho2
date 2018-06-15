package command

import context.Context
import expression._
import exceptions._
import types._
import value._

class Return (
  val retVal: Expression[Type]
  ) extends Command {

  def execute(ctx: Context): Option[Value[Type]] = {
    Some(retVal.eval(ctx))
  }
}

object Return {
  def apply(retVal: Expression[Type]): Return = new Return(retVal)
}
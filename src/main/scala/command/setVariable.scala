package command

import context.Context
import expression._
import value._
import types._

class SetVariable(val varName: String, val varValue: Expression[Type]) extends Command {
  def execute(ctx: Context): Option[Value[Type]] = {
    ctx.setVar(varName, varValue.eval(ctx))
    None
  }
}

object SetVariable {
  def apply(pair: (String, Expression[Type]))           : SetVariable = new SetVariable(pair._1, pair._2)
  def apply(varName: String, varValue: Expression[Type]): SetVariable = new SetVariable(varName, varValue)
}
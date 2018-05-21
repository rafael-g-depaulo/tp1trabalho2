package command

import context.Context
import value._
import types._

class SetVariable(val varName: String, val varValue: Value[Type]) extends Command {
  def execute(ctx: Context) {
    ctx.setVar(varName, varValue)
  }
}

object SetVariable {
  def apply(pair: (String, Value[Type])): SetVariable = new SetVariable(pair._1, pair._2)
  def apply(varName: String, varValue: Value[Type]): SetVariable = new SetVariable(varName, varValue)
}
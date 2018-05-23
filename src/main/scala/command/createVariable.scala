package command

import context.Context
import value._
import types._

class CreateVariable(val varName: String, val varValue: Value[Type]) extends Command {
  def execute(ctx: Context) {
    ctx.createVar(varName, varValue)
  }
}

object CreateVariable {
  def apply(pair: (String, Value[Type])): CreateVariable = new CreateVariable(pair._1, pair._2)
  def apply(varName: String, varValue: Value[Type]): CreateVariable = new CreateVariable(varName, varValue)
}
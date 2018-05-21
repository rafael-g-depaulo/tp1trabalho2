package command

import context.Context
import value._
import types._

case class SetVariable(val varName: String, val varValue: Value[Type]) extends Command {
  def execute(ctx: Context) {
    ctx.setVar(varName, varValue)
  }
}
package command

import context.Context
import function._
import value._
import types._

class CreateFunction(val funcName: String, val func: Function) extends Command {
  def execute(ctx: Context) {
    ctx.createFunc(funcName, func)
  }
}

object CreateFunction {
  def apply(pair: (String, Function)): CreateFunction = new CreateFunction(pair._1, pair._2)
  def apply(funcName: String, func: Function): CreateFunction = new CreateFunction(funcName, func)
}
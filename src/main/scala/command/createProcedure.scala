package command

import context.Context
import procedure._
import value._
import types._

class CreateProcedure(val funcName: String, val func: Procedure) extends Command {
  def execute(ctx: Context) {
    ctx.createProcd(funcName, func)
  }
}

object CreateProcedure {
  def apply(pair: (String, Procedure)): CreateProcedure = new CreateProcedure(pair._1, pair._2)
  def apply(funcName: String, func: Procedure): CreateProcedure = new CreateProcedure(funcName, func)
}
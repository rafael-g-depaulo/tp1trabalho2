package command

import context.Context
import expression._
import types._
import value._

class IfThenElse(
  private val ctx: Context,
  private val cond: Expression[TypeBool],
  private val blcIf: Block,
  private val blcElse: Block) extends Command(ctx) {

  def execute() {
    ctx.addLayer
    if (cond.eval(ctx) == Value(TypeBool(true)))
      blcIf.execute
    else
      blcElse.execute
    ctx.removeLayer
  }
}
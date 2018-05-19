package command

import context.Context
import expression._
import types._
import value._

class IfThenElse(
  private val cond: Expression[TypeBool],
  private val blcIf: Block,
  private val blcElse: Block) extends Command {

  def execute(ctx: Context) {
    ctx.addLayer
    if (cond.eval(ctx) == Value(TypeBool(true)))
      blcIf.execute(ctx)
    else
      blcElse.execute(ctx)
    ctx.removeLayer
  }
}
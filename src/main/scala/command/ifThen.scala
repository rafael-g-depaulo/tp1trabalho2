package command

import context.Context
import expression._
import types._
import value._

class IfThen(
  private val cond: Expression[TypeBool],
  private val blc: Block) extends Command {

  def execute(ctx: Context) {
    if (cond.eval(ctx) == Value(TypeBool(true))) {
      ctx.addLayer
      blc.execute(ctx)
      ctx.removeLayer
    }
  }
}

object IfThen {
  def apply(cond: Expression[TypeBool], blc: Block): IfThen = new IfThen(cond, blc)
}
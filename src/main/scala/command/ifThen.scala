package command

import context.Context
import expression._
import types._
import value._

class IfThen(
  private val cond: Expression[TypeBool],
  private val blc: Command) extends Command {

  def execute(ctx: Context): Option[Value[Type]] = {
    if (cond.eval(ctx) == Value(TypeBool(true))) {
      ctx.addLayer
      blc.execute(ctx) match {
        case None        => ctx.removeLayer; None
        case Some(value) => ctx.removeLayer; Some(value)
      }
    }
    else None
  }
}

object IfThen {
  def apply(cond: Expression[TypeBool], blc: Command): IfThen = new IfThen(cond, blc)
}
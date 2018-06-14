package command

import context.Context
import expression._
import types._
import value._

import scala.reflect.ClassTag

class IfThenElse(
  private val cond: Expression[TypeBool],
  private val blcIf: Command,
  private val blcElse: Command) extends Command {

  def execute(ctx: Context) {
    ctx.addLayer
    if (cond.eval(ctx) == Value(TypeBool(true)))
      blcIf.execute(ctx)
    else
      blcElse.execute(ctx)
    ctx.removeLayer
  }
}

object IfThenElse {
  def apply(cond: Expression[TypeBool], blc: Command, blcElse: Command): IfThenElse = new IfThenElse(cond, blc, blcElse)
  def apply[A : ClassTag](cond: Expression[TypeBool])(blc: Command)(blcElse: Command): IfThenElse = new IfThenElse(cond, blc, blcElse)
}
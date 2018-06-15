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

  def execute(ctx: Context): Option[Value[Type]] = {
    ctx.addLayer
    if (cond.eval(ctx) == Value(TypeBool(true))) {
      blcIf.execute(ctx) match {
        case None        => ctx.removeLayer; None
        case Some(value) => ctx.removeLayer; Some(value)
      }
    }
    else {
      blcElse.execute(ctx) match {
        case None        => ctx.removeLayer; None
        case Some(value) => ctx.removeLayer; Some(value)
      }
    }
  }
}

object IfThenElse {
  def apply(cond: Expression[TypeBool], blc: Command, blcElse: Command): IfThenElse = new IfThenElse(cond, blc, blcElse)
  def apply[A : ClassTag](cond: Expression[TypeBool])(blc: Command)(blcElse: Command): IfThenElse = new IfThenElse(cond, blc, blcElse)
}
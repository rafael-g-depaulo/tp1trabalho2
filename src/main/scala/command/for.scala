package command

import context.Context
import expression._
import types._
import value._

class For(val doOnce: Command, val cond: Expression[TypeBool], val cmd: Command, val alwaysDo: Command) extends Command { 
  def execute(ctx: Context): Option[Value[Type]] = {
    doOnce.execute(ctx)
    def iterate: Option[Value[Type]] = {
      cond.eval(ctx).innerValue match {
        case TypeBool(false) => ctx.removeLayer; None
        case TypeBool(true)  => cmd.execute(ctx) match {
          case None       => alwaysDo.execute(ctx); iterate //; repete
          case Some(body) => ctx.removeLayer; return Some(body)
        }
      }
    }
    ctx.addLayer
    iterate
  }
}


object For {
  def apply(doOnce: Command, cond: Expression[TypeBool], cmd: Command, alwaysDo: Command): For = new For(doOnce, cond, cmd, alwaysDo)
}
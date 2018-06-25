package command

import context.Context
import expression._
import types._
import value._

class DoWhile(cond: Expression[TypeBool], cmd: Command) extends Command {
  def execute(ctx: Context): Option[Value[Type]] = {
    def iterate: Option[Value[Type]] = {
      cond.eval(ctx).innerValue match {
        case TypeBool(false) => ctx.removeLayer; None
        case TypeBool(true)  => cmd.execute(ctx) match {
          case None       => iterate
          case Some(body) => ctx.removeLayer; return Some(body)
        }
      }
    }
    ctx.addLayer
    cmd.execute(ctx)
    iterate
  }
}

object DoWhile {
  def apply(cond: Expression[TypeBool], cmd: Command): DoWhile = new DoWhile(cond, cmd)
}
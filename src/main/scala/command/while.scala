package command

import context.Context
import expression._
import types._
import value._

class While(
  val cond: Expression[TypeBool],
  val cmd: Command) extends Command {
    
  def execute(ctx: Context): Option[Value[Type]] = {
    def iterate: Option[Value[Type]] = {
      cond.eval(ctx).innerValue match {
        case TypeBool(false) => ctx.removeLayer; None
        case TypeBool(true)  => cmd.execute(ctx) match {
          case None        => iterate
          case Some(value) => ctx.removeLayer; Some(value);
        }
      }
    }

    ctx.addLayer
    iterate
  }
}


object While {
  def apply(cond: Expression[TypeBool], cmd: Command): While = new While(cond, cmd)
}
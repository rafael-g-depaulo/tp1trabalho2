package procedure

import expression._
import types._
import command._
import context._

case class Procedure(
  val body: Block
) {
  def execute(ctx: Context, params: (String, Expression[Type])*) {
    ctx.addLayer()
    // params foreach {
    //   (param: (String, Expression[Type])) => context.setVar(param._1, param._2.eval(context))
    // }
    for (param <- params)
      ctx.setVar(param._1, param._2.eval(ctx))

    body.execute(ctx)
    ctx.removeLayer()
  }
}
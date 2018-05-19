package procedure

import expression._
import types._
import command._
import context._

case class Procedure(
  body: Block,
  params: (String, Expression[Type])*
) {
  def execute(ctx: Context) {
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
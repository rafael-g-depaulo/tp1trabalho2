package procedure

import expression._
import exceptions._
import types._
import value._
import command._
import context._

class Procedure(
  val body: Block,
  _paramNames: String*
) {
  val paramNames = List(_paramNames: _*)
  def call(ctx: Context)(params: (String, Expression[Type])*) {
    ctx.addLayer()

    // creating the variables
    paramNames foreach { ctx.createVar((_: String), UndefinedValue)}

    // setting them
    try {
      for (param <- params)
        ctx.setVar(param._1, param._2.eval(ctx))
    } catch {
      case err: InexistentVariable => throw WrongParameterName("parametro inicializado com nome invalido")
    }

    // if there are Undefined parameters, throw exception
    for (param <- paramNames)
      if (ctx.getVar(param) == UndefinedValue) throw IncompleArgumentList("lista de argumentos incompleta para procedimento")

    // execute procedure
    body.execute(ctx)

    // remove layer
    ctx.removeLayer()
  }
}

object Procedure {
  def apply(body: Block, paramNames: String*): Procedure = new Procedure(body, paramNames: _*)
  def apply(paramNames: String*)(body: Block): Procedure = new Procedure(body, paramNames: _*)
}
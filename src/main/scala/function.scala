package function

import scala.util.control.Breaks._

import expression._
import procedure._
import exceptions._
import types._
import value._
import command._
import context._
import ed.immutable.List

class Function(
  val body: Command,
  _paramNames: String*
  ) {
  val paramNames = List(_paramNames: _*)
  def call(ctx: Context)(params: (String, Expression[Type])*): Value[Type] = {
    ctx.addLayer()
    var retVal: Value[Type] = UndefinedValue

    // creating the variables
    paramNames foreach { ctx.createVar((_: String), UndefinedValue)}

    // setting them
    try {
      for (param <- params)
        ctx.setVar(param._1, param._2.eval(ctx))
    } catch {
      case err: InexistentThing => throw WrongParameterName("parametro inicializado com nome invalido")
    }

    // if there are Undefined parameters, throw exception
    for (param <- paramNames)
      if (ctx.getVar(param) == UndefinedValue) throw IncompleArgumentList("lista de argumentos incompleta para procedimento")

    // if body is a command, create a Block with it inside and treat it as the body
    val bodyAsBlock: Block = body match {
      case Block(cmds) => body.asInstanceOf[Block]
      case _           => body.asBlock 
    }

    bodyAsBlock.execThenReturn(ctx)
  }
}

object Function {
  def apply(body: Command, paramNames: String*): Function = new Function(body, paramNames: _*)
  def apply(paramNames: String*)(body: Command): Function = new Function(body, paramNames: _*)
}
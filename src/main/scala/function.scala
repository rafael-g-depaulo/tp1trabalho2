package function

import scala.util.control.Breaks._

import expression._
import procedure._
import exceptions._
import types._
import value._
import command._
import context._

class Function(
  val body: Block,
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

    // execute function until Return is found
    val cmdIter = body.commands.getIterator()
    // loops through all-but-last commands
    do {
      if (cmdIter.value.isReturn) {
        val retVal = cmdIter.value.returnValue(ctx)
        ctx.removeLayer()
        return retVal
      }
      else { cmdIter.value.execute(ctx) }
      if (cmdIter.hasNext) cmdIter.next
    } while (cmdIter.hasNext)
    // checks last command
    if (cmdIter.value.isReturn) {
      val retVal = cmdIter.value.returnValue(ctx)
      ctx.removeLayer()
      return retVal
    }
    // reached end and nothing was returned
    else throw FunctionWithoutReturn("função chegou ao fim e nenhum valor foi retornado")
  }
}

object Function {
  def apply(body: Block, paramNames: String*): Function = new Function(body, paramNames: _*)
  def apply(paramNames: String*)(body: Block): Function = new Function(body, paramNames: _*)
}
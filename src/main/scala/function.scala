package function

import scala.util.control.Breaks._
import scala.reflect.runtime.universe.{TypeTag, typeTag}
import scala.reflect.runtime.universe

import expression._
import procedure._
import exceptions._
import types._
import value._
import command._
import context._
import ed.mutable._
import ed.immutable.List

import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}
import scala.reflect.runtime.universe

class Function(
  val body: Command,
  _params: (String, universe.Type)*
  )
  {
  val paramNames: List[String] = List(_params.map(_._1): _*)
  val paramTypes = HashTable[String, universe.Type](50, _params.map(p => (p._1, p._2)): _*)
  def call(ctx: Context)(params: (String, Expression[Type])*): Value[Type] = {
    ctx.addLayer()
    var retVal: Value[Type] = UndefinedValue

    // creating local parameter variables
    paramNames foreach { ctx.createVar((_: String), UndefinedValue)}

    // checking types
    params foreach {
      param: (String, Expression[Type]) => {
        paramTypes.get(param._1) match {
        // if parameter with wrong name inserted
          case None => throw WrongParameterName("parametro inicializado com nome invalido")
        // else
          case Some(tt) => tt match {
          // if type was the same as expected
            case t if t =:= param._2.getExprType => ctx.setVar(param._1, param._2.eval(ctx))// okay
            case _                         => throw IncompatibleTypeException("Tipo incompativel. Esperava: "+param._2.getExprType+". Recebido: "+tt)
          }
        }
      }
    }

    // setting them
    // try {
    //   for (param <- params)
    //     ctx.setVar(param._1, param._2.eval(ctx))
    // } catch {
    //   case err: InexistentThing => throw WrongParameterName("parametro inicializado com nome invalido")
    // }

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
  // def apply(body: Command, params: (String, Type)*): Function = new Function(body, params: _*)
  def apply(params: (String, universe.Type)*)(body: Command): Function = new Function(body, params: _*)
}
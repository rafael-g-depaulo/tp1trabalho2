package procedure

import expression._
import exceptions._
import types._
import value._
import command._
import context._
import ed.mutable._
import ed.immutable.List

import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}
import scala.reflect.runtime.universe

class Procedure(
  val body: Command,
  _params: (String, universe.Type)*,
) {
  val paramNames: List[String] = List(_params.map(_._1): _*)
  val paramTypes = HashTable[String, universe.Type](20, _params: _*)
  def call(ctx: Context)(params: (String, Expression[Type])*) {
    ctx.addLayer()

    // creating local parameter variables
    paramNames foreach { 
      (name) => {
        paramTypes.get(name) match {
          case None    => // nunca vai acontecer
          case Some(t) => ctx.createVar(t)(name, UndefinedValue)
        }
      }
    }

    // checking types
    params foreach {
      param: (String, Expression[Type]) => {
        paramTypes.get(param._1) match {
        // if parameter with wrong name inserted
          case None => throw WrongParameterName("parametro inicializado com nome invalido")
        // else
          case Some(tt) => tt match {
          // if type was the same as expected, set variable
            case t if t =:= param._2.retType => ctx.setVar(param._1, param._2.eval(ctx), t)
          // if not, throw exception
            case _ => throw IncompatibleTypeException("Tipo incompativel. Esperava: "+tt+". Recebido: "+param._2+", tipo:"+param._2.retType)
          }
        }
      }
    }

    // if there are Undefined parameters, throw exception
    for (param <- paramNames) {
      paramTypes.get(param) match {
        case None    => // nunca vai acontecer
        case Some(t) => if (ctx.getVar(t)(param) == UndefinedValue) throw IncompleArgumentList("lista de argumentos incompleta para procedimento")
      }
      
    }

    body.execute(ctx)
    ctx.removeLayer()
  }
}

object Procedure {
  def apply(params: (String, universe.Type)*)(body: Command): Procedure = new Procedure(body, params: _*)
  def apply(body: Command, params: (String, universe.Type)*): Procedure = new Procedure(body, params: _*)

}
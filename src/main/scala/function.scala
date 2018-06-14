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

class Function[+T <: Type](
  val body: Command,
  // val ev: TypeTag[T],
  _params: (String, universe.Type)*,
  )
  {
  val paramNames: List[String] = List(_params.map(_._1): _*)
  val paramTypes = HashTable[String, universe.Type](50, _params.map(p => (p._1, p._2)): _*)
  def call[T1 >: T <: Type : TypeTag](ctx: Context)(params: (String, Expression[Type])*): Value[Type] = {
    ctx.addLayer()

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
          // if type was the same as expected, set variable
            case t if t =:= param._2.retType => ctx.setVar(param._1, param._2.eval(ctx))
          // if not, throw exception
            case _ => throw IncompatibleTypeException("Tipo incompativel. Esperava: "+tt+". Recebido: "+param._2+", tipo:"+param._2.retType)
          }
        }
      }
    }

    // if there are Undefined parameters, throw exception
    for (param <- paramNames)
      if (ctx.getVar(param) == UndefinedValue) throw IncompleArgumentList("lista de argumentos incompleta para procedimento")

    // if body is a command, create a Block with it inside and treat it as the body
    val bodyAsBlock: Block = body match {
      case Block(cmds) => body.asInstanceOf[Block]
      case _           => body.asBlock 
    }

    val retVal = bodyAsBlock.execThenReturn(ctx)
    if (!(retVal.retType =:= typeOf[T1]))
      throw IncompatibleTypeException("Tipo de retorno de funcao errado. Era pra retornar: "+typeOf[T1]+". Retornou: "+retVal.retType)
    retVal
  }
}

object Function {
  // def apply(body: Command, params: (String, Type)*): Function = new Function(body, params: _*)
  def apply[T <: Type](params: (String, universe.Type)*)(body: Command)(implicit ev: TypeTag[T]): Function[T] = new Function[T](body, params: _*)
}
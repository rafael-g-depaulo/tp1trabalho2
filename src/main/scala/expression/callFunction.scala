package expression

import types._
import context.Context
import value.Value
import ed.mutable._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class CallFunction(val funcName: String, val _params: (String, Expression[Type])*) extends Expression[Type] {
//   val params = List(_params: _*)
  def eval[T1 /*>: T*/ <: Type](context: Context): Value[T1] = context.getFunc(funcName).call(context)(_params: _*).asInstanceOf[Value[T1]]
  def getExprType[T1 /*>: T*/ <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[T1]
}

object CallFunction {
    def apply[T <: Type](funcName: String)(params: (String, Value[Type])*): CallFunction = new CallFunction(funcName, params: _*)
}
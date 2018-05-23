package expression

import types._
import context.Context
import value.Value
import ed.mutable._

class CallFunction /*[+T <: Type]*/ (val funcName: String, val _params: (String, Expression[Type])*) extends Expression[Type] {
//   val params = List(_params: _*)
  def eval[T1 /*>: T*/ <: Type](context: Context): Value[T1] = context.getFunc(funcName).call(context)(_params: _*).asInstanceOf[Value[T1]]
}

object CallFunction {
    def apply[T <: Type](funcName: String)(params: (String, Value[Type])*): CallFunction = new CallFunction(funcName, params: _*)
}
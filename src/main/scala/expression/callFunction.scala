package expression

import types._
import context.Context
import value.Value
import ed.mutable._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class CallFunction[T <: Type](val funcName: String, val ev: TypeTag[T], val _params: (String, Expression[Type])*) extends Expression[T](ev.tpe) {
//   val params = List(_params: _*)
  def eval[T1 >: T <: Type](context: Context): Value[T1] = {
    context.getFunc(funcName).call[T](context)(_params: _*)(ev).asInstanceOf[Value[T1]]
  }
  // def getExprType[T1 >: T <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[T1]
}

object CallFunction {
    def apply[T <: Type](funcName: String)(params: (String, Value[Type])*)(implicit ev: TypeTag[T]): CallFunction[T] = new CallFunction[T](funcName, ev, params: _*)
}
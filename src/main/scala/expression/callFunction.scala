package expression

import types._
import context.Context
import value.Value
import ed.mutable._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class CallFunction[T <: Type](val funcName: String, val ev: TypeTag[T], val _params: (String, Expression[Type])*) extends Expression[T](ev.tpe) {
  def eval[T1 >: T <: Type](context: Context): Value[T1] =
    context.getFunc[T](funcName).call[T](context)(_params.map(p => (p._1, p._2.eval(context))): _*)(ev).asInstanceOf[Value[T1]]
}

object CallFunction {
    def apply[T <: Type](funcName: String)(params: (String, Expression[Type])*)(implicit ev: TypeTag[T]): CallFunction[T] = new CallFunction[T](funcName, ev, params: _*)
}
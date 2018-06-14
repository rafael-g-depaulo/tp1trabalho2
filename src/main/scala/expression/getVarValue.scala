package expression

import types._
import context.Context
import value.Value
import ed.mutable._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class GetVarValue[+T <: Type](val varName: String, ev: TypeTag[T]) extends Expression[T](ev.tpe) {
  def eval[T1 >: T <: Type](context: Context): Value[T1] = context.getVar(varName).asInstanceOf[Value[T1]]	
  // def getExprType[T1 >: T <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[T1]
}

object GetVarValue {
  def apply[T <: Type](varName: String)(implicit ev: TypeTag[T]): GetVarValue[T] = new GetVarValue[T](varName, ev)
}
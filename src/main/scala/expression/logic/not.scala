package expression
package logic

import types._
import value._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class NotGate(_param: Expression[TypeBool]) extends UnExpression[TypeBool, TypeBool](_param, typeOf[TypeBool]) {
  def eval[T1 >: TypeBool <: Type](context: Context): Value[T1] =
    Value[TypeBool](TypeBool(!_param.eval(context).innerValue())).asInstanceOf[Value[T1]]

  // def getExprType[T1 >: TypeBool <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeBool]    
}

object NotGate {
  def apply(param: Expression[TypeBool]): NotGate = new NotGate(param)
}
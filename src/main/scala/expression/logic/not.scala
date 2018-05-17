package expression
package logic

import types._
import value._
import context.Context

case class NotGate(_param: Expression[TypeBool]) extends UnExpression[TypeBool, TypeBool](_param) {
  def eval[T1 >: TypeBool <: Type](context: Context): Value[T1] =
    Value[TypeBool](TypeBool(!_param.eval(context).innerValue())).asInstanceOf[Value[T1]]

}
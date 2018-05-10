package expression
package logic

import types._
import value._

case class NotGate(_param: Expression[TypeBool]) extends UnExpression[TypeBool, TypeBool](_param) {
  // def eval(): Value[TypeBool] = Value[TypeBool](TypeBool(!_param.eval().innerValue()))
  def eval[T1 >: TypeBool <: Type](): Value[T1] = Value[TypeBool](TypeBool(!_param.eval().innerValue())).asInstanceOf[Value[T1]]

}
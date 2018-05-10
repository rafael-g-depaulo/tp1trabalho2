package expression
package math

import types._
import value._

case class OrExpression(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool]) extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
  // def eval(): Value[TypeBool] = Value[TypeBool](TypeBool(lhs.eval().innerValue() || rhs.eval().innerValue()))
  def eval[T1 >: TypeBool <: Type](): Value[T1] = Value[TypeBool](TypeBool(lhs.eval().innerValue() || rhs.eval().innerValue())).asInstanceOf[Value[T1]]
}
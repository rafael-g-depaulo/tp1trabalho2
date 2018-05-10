package expression
package math

import types._
import value._

case class ModExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {
  // def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() % rhs.eval().innerValue()))
  def eval[T1 >: TypeInt <: Type](): Value[T1] = Value[TypeInt](TypeInt(lhs.eval().innerValue() % rhs.eval().innerValue())).asInstanceOf[Value[T1]]
}
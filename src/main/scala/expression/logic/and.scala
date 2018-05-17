package expression
package logic

import types._
import value._
import context.Context

case class AndGate(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool], _context: Context)
  extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs, _context) {
  // def eval(): Value[TypeBool] = Value[TypeBool](TypeBool(lhs.eval().innerValue() && rhs.eval().innerValue()))
  def eval[T1 >: TypeBool <: Type](): Value[T1] = Value[TypeBool](TypeBool(lhs.eval().innerValue() && rhs.eval().innerValue())).asInstanceOf[Value[T1]]
}

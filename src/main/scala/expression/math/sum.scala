package expression
package math

import types._
import value._
import context.Context

case class SumExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt], _context: Context)
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs, _context) {
    
  def eval[T1 >: TypeInt <: Type](): Value[T1] = Value[TypeInt](TypeInt(lhs.eval().innerValue() + rhs.eval().innerValue())).asInstanceOf[Value[T1]]
  // def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() + rhs.eval().innerValue()))
}
package expression
package math

import types._
import value._
import context.Context

final case class NegativeDenomException (msg: String) extends Exception(msg)

case class DivExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt], _context: Context)
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs, _context) {

  // checa que o denominador não vai ser 0
  if (rhs.eval() == Value(TypeInt(0)))
    throw NegativeDenomException("Expressao de divisao inicializada com denominador 0")

  // def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() / rhs.eval().innerValue()))
  def eval[T1 >: TypeInt <: Type](): Value[T1] = Value[TypeInt](TypeInt(lhs.eval().innerValue() / rhs.eval().innerValue())).asInstanceOf[Value[T1]]

}
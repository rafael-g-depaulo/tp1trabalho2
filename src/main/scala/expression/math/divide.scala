package expression
package math

import types._
import value._

final case class NegativeDenomException (msg: String) extends Exception(msg)

<<<<<<< HEAD
case class DivExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt](_lhs, _rhs) {
=======
case class DivExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {
>>>>>>> logic

  // checa que o denominador não vai ser 0
  if (rhs.eval() == Value(TypeInt(0)))
    throw NegativeDenomException("Expressao de divisao inicializada com denominador 0")

  def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() / rhs.eval().innerValue()))
}
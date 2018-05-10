package expression
package math

import types._
import value._

case class SubExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {
  def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() - rhs.eval().innerValue()))
}
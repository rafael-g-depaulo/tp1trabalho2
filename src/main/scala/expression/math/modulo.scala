package expression
package math

import types._
import value._

case class ModExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt](_lhs, _rhs) {
  def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() % rhs.eval().innerValue()))
}
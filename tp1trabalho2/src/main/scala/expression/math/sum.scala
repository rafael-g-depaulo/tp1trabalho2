package expression
package math

import types._
import value._

case class SumExpression(_lhs: Value[TypeInt], _rhs: Value[TypeInt]) extends BinExpression[TypeInt](_lhs, _rhs) {
  def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.innerValue() + rhs.innerValue()))
}
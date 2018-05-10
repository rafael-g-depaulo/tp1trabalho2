package expression
package math

import types._
import value._

<<<<<<< HEAD
case class ModExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt](_lhs, _rhs) {
=======
case class ModExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {
>>>>>>> logic
  def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.eval().innerValue() % rhs.eval().innerValue()))
}
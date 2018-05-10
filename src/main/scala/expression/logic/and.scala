<<<<<<< HEAD
// package expression
// package math

// import types._
// import value._

// case class AndExpression(_lhs: Value[TypeBool], _rhs: Value[TypeBool]) extends BinExpression[TypeInt](_lhs, _rhs) {
//   def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.innerValue() % rhs.innerValue()))
// }
=======
package expression
package math

import types._
import value._

case class AndExpression(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool]) extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
  def eval(): Value[TypeBool] = Value[TypeBool](TypeBool(lhs.eval().innerValue() && rhs.eval().innerValue()))
}
>>>>>>> logic

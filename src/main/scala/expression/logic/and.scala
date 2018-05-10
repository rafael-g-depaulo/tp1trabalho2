// package expression
// package math

// import types._
// import value._

// case class AndExpression(_lhs: Value[TypeBool], _rhs: Value[TypeBool]) extends BinExpression[TypeInt](_lhs, _rhs) {
//   def eval(): Value[TypeInt] = Value[TypeInt](TypeInt(lhs.innerValue() % rhs.innerValue()))
// }
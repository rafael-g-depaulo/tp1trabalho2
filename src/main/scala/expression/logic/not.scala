package expression
package math

import types._
import value._

case class NotExpression(_param: Expression[TypeBool]) extends UnExpression[TypeBool, TypeBool](_param) {
  def eval(): Value[TypeBool] = Value[TypeBool](TypeBool(!_param.eval().innerValue()))
}
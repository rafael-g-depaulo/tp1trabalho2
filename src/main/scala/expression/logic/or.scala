package expression
package logic

import types._
import value._
import context.Context

case class OrGate(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool])
  extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
  def eval[T1 >: TypeBool <: Type](context: Context): Value[T1] =
    Value[TypeBool](TypeBool(lhs.eval(context).innerValue() || rhs.eval(context).innerValue())).asInstanceOf[Value[T1]]
}
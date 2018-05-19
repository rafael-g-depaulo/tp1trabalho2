package expression
package math

import types._
import value._
import context.Context

case class SumExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt])
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {
    
  def eval[T1 >: TypeInt <: Type](context: Context): Value[T1] =
    Value[TypeInt](TypeInt(lhs.eval(context: Context).innerValue() + rhs.eval(context: Context).innerValue())).asInstanceOf[Value[T1]]
}
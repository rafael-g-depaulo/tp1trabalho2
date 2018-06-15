package expression
package math

import types._
import value._
import exceptions._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class SubExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt])
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs, typeOf[TypeInt]) {
  def eval[T1 >: TypeInt <: Type](context: Context): Value[T1] =
    Value[TypeInt](TypeInt(lhs.eval(context).innerValue() - rhs.eval(context).innerValue())).asInstanceOf[Value[T1]]
}

object SubExpression {
  def apply(lhs: Expression[TypeInt], rhs: Expression[TypeInt]): SubExpression = new SubExpression(lhs, rhs)
}
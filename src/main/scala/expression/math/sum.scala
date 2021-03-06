package expression
package math

import types._
import value._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class SumExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt])
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs, typeOf[TypeInt]) {
    
  def eval[T1 >: TypeInt <: Type](context: Context): Value[T1] =
    Value[TypeInt](TypeInt(lhs.eval(context: Context).innerValue() + rhs.eval(context: Context).innerValue())).asInstanceOf[Value[T1]]

  // def getExprType[T1 >: TypeInt <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeInt]    
}

object SumExpression {
  def apply(lhs: Expression[TypeInt], rhs: Expression[TypeInt]): SumExpression = new SumExpression(lhs, rhs)
}
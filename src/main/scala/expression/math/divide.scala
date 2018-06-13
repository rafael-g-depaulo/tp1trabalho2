package expression
package math

import types._
import value._
import context.Context
import exceptions._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class DivExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt])
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {

  def eval[T1 >: TypeInt <: Type](context: Context): Value[T1] = {
    val rhsEval: Value[TypeInt] = rhs.eval(context)
    if (rhs.eval(context) == Value(TypeInt(0)))
      throw NegativeDenomException("Expressao de divisao inicializada com denominador 0")
    else
      Value[TypeInt](TypeInt(lhs.eval(context).innerValue() / rhsEval.innerValue())).asInstanceOf[Value[T1]]
  }
  
  def getExprType[T1 >: TypeInt <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeInt]
}

object DivExpression {
  def apply(lhs: Expression[TypeInt], rhs: Expression[TypeInt]): DivExpression = new DivExpression(lhs, rhs)
}
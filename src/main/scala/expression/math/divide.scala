package expression
package math

import types._
import value._
import context.Context

final case class NegativeDenomException (msg: String) extends Exception(msg)

case class DivExpression(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt])
  extends BinExpression[TypeInt, TypeInt, TypeInt](_lhs, _rhs) {

  def eval[T1 >: TypeInt <: Type](context: Context): Value[T1] = {
    val rhsEval: Value[TypeInt] = rhs.eval(context)
    if (rhs.eval(context) == Value(TypeInt(0)))
      throw NegativeDenomException("Expressao de divisao inicializada com denominador 0")
    else
      Value[TypeInt](TypeInt(lhs.eval(context).innerValue() / rhsEval.innerValue())).asInstanceOf[Value[T1]]
  }

}
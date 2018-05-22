package expression
package logic

import types._
import value._
import context.Context

/** Implementação da operação lógica XOR Gate
 * @param duas expressões de Bool
 * @return um valor Bool (Value[TypeBool]) true caso as duas expressões reduzam para valores diferentes, e false caso contrário
 */

case class XorGate(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool])
  extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
  def eval[T1 >: TypeBool <: Type](context: Context): Value[T1] =
    Value[TypeBool](TypeBool(lhs.eval(context).innerValue() != rhs.eval(context).innerValue())).asInstanceOf[Value[T1]]
}

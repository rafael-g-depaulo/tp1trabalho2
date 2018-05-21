package expression
package comparable

import value._
import types._
import context.Context

/** Implementação da expressão de comparação "maior que".
 * @param 2 expressões de int (Expression[TypeInt])
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja maior que a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

case class GreaterThan(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeBool, TypeInt, TypeInt](_lhs, _rhs) {

	def eval[T1 >: TypeBool <: Type](ctx: Context): Value [T1] = Value[TypeBool](TypeBool(lhs.eval(ctx).innerValue() > rhs.eval(ctx).innerValue())).asInstanceOf[Value [T1]]

}

package expression
package comparable

import types._
import value._
import context.Context

/** Implementação da expressão de comparação "igual" para Booleans.
 * @param 2 expressões de bool																																																																																																																														
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja igual a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

class EqualBool(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool]) extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
	def eval[T1 >: TypeBool <: Type](ctx: Context): Value [T1] = Value[TypeBool](TypeBool(lhs.eval(ctx).innerValue() == rhs.eval(ctx).innerValue())).asInstanceOf[Value [T1]]
}

object EqualBool {
	def apply(lhs: Expression[TypeBool], rhs: Expression[TypeBool]): EqualBool = new EqualBool(lhs, rhs)
}
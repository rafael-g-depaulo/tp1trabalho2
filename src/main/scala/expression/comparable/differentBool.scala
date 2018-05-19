package expression
package comparable

import types._
import value._

/** Implementação da expressão de comparação "diferente" para Booleans.
 * @param 2 expressões de bool																																																																																																																														
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja diferente a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

case class DifferentBool(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool]) extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {

	def eval[T1 >: TypeBool <: Type](): Value [T1] = Value[TypeBool](TypeBool(lhs.eval().innerValue() != rhs.eval().innerValue())).asInstanceOf[Value [T1]]

}

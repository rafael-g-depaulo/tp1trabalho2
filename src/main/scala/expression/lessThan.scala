package expression
package comparable

import types._
import value._

/** Implementação da expressão de comparação "menor que".
 * @param 2 expressões de int (Expression[TypeInt])
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja menor que a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

case class LessThan(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeBool, TypeInt, TypeInt](_lhs, _rhs) {

	def eval[T1 >: TypeBool <: Type](): Value[T1] = Value[TypeBool](TypeBool(lhs.eval().innerValue() < rhs.eval().innerValue())).asInstanceOf[Value[T1]]

}

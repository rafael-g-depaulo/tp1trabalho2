package expression
package comparable

import types._
import value._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

/** Implementação da expressão de comparação "diferente" para Booleans.
 * @param 2 expressões de bool																																																																																																																														
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja diferente a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

case class DifferentBool(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool]) extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs, typeOf[TypeInt]) {
	def eval[T1 >: TypeBool <: Type](ctx: Context): Value [T1] = Value[TypeBool](TypeBool(lhs.eval(ctx).innerValue() != rhs.eval(ctx).innerValue())).asInstanceOf[Value [T1]]
	
  // def getExprType[T1 >: TypeBool <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeBool]
}

object DifferentBool {
	def apply(lhs: Expression[TypeBool], rhs: Expression[TypeBool]): DifferentBool = new DifferentBool(lhs, rhs)
}
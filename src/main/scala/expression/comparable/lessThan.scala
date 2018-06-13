package expression
package comparable

import types._
import value._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

/** Implementação da expressão de comparação "menor que".
 * @param 2 expressões de int (Expression[TypeInt])
 * @return 1 valor bool (Value[TypeBool]) true caso expressão 1 seja menor que a expressão 2, false em caso contrário
 * @author Lucas V. M. Pinheiro
 */

class LessThan(_lhs: Expression[TypeInt], _rhs: Expression[TypeInt]) extends BinExpression[TypeBool, TypeInt, TypeInt](_lhs, _rhs) {
	def eval[T1 >: TypeBool <: Type](ctx: Context): Value[T1] = Value[TypeBool](TypeBool(lhs.eval(ctx).innerValue() < rhs.eval(ctx).innerValue())).asInstanceOf[Value[T1]]
	
  def getExprType[T1 >: TypeBool <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeBool]
}

object LessThan {
	def apply(lhs: Expression[TypeInt], rhs: Expression[TypeInt]): LessThan = new LessThan(lhs, rhs)
}
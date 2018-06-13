package expression
package logic

import types._
import value._
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class OrGate(_lhs: Expression[TypeBool], _rhs: Expression[TypeBool])
  extends BinExpression[TypeBool, TypeBool, TypeBool](_lhs, _rhs) {
  def eval[T1 >: TypeBool <: Type](context: Context): Value[T1] =
    Value[TypeBool](TypeBool(lhs.eval(context).innerValue() || rhs.eval(context).innerValue())).asInstanceOf[Value[T1]]

  def getExprType[T1 >: TypeBool <: Type](implicit ev: TypeTag[T1]): universe.Type = typeOf[TypeBool]    
}

object OrGate {
  def apply(lhs: Expression[TypeBool], rhs: Expression[TypeBool]): OrGate = new OrGate(lhs, rhs)
}
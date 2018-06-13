package value

import ed.exceptions._
import types._
import context.Context
import exceptions._
import expression.Expression

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

abstract sealed class Value[+T <: Type] extends Expression[T]() {
  // def eval[T1 >: T](context: Map[String, Value[Type]]): Value[T1] = this
  def innerValue: T
  def eval[T1 >: T <: Type](context: Context): Value[T1] = this
}

private case class ValCreate[+T <: Type](val value: T) extends Value[T] {
  def innerValue: T = value
  override def getExprType[T1 >: T <: Type](implicit ev: TypeTag[T1]): universe.Type = value match {
    case _: TypeInt  => typeOf[TypeInt]
    case _: TypeBool => typeOf[TypeBool]
    case _           => throw ShoulNotHappenException("match em value.ValCreate nao foi exaustivo. novo tipo criado e mal implementado") 
  }
}

object Value {
  def apply[T <: Type](): Value[T] = UndefinedValue.asInstanceOf[Value[T]]
  def apply[T <: Type](value: T): Value[T] = ValCreate[T](value)
}

case object UndefinedValue extends Value[Nothing] {
  def getExprType[T1 >: Nothing <: Type](implicit ev: TypeTag[T1]): universe.Type = throw AccessingUndefinedException("tentando acessar o tipo de Undefined") 
  override def innerValue: Nothing                                          = throw AccessingUndefinedException("tentando pegar valor de Undefined") 
  override def eval[T1 >: Nothing <: Type](context: Context): Value[T1]     = throw AccessingUndefinedException("tentando avaliar(evaluate) Undefined") 
}
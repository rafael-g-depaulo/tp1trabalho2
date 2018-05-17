package value

import ed.exceptions._
import types._
import context.Context
import expression.Expression

abstract sealed class Value[+T <: Type] extends Expression[T](new Context) {
  // def eval[T1 >: T](context: Map[String, Value[Type]]): Value[T1] = this
  def innerValue: T
  def eval[T1 >: T <: Type](): Value[T1] = this
}

private case class ValCreate[+T <: Type](val value: T) extends Value[T] {
  def innerValue: T = value
}

object Value {
  def apply[T <: Type](): Value[T] = UndefinedValue.asInstanceOf[Value[T]]
  def apply[T <: Type](value: T): Value[T] = ValCreate[T](value)
}

case object UndefinedValue extends Value[Nothing] {
  override def innerValue: Nothing                         = throw AccessingUndefinedException("tentando pegar valor de Undefined") 
  override def eval[T1 >: Nothing <: Type](): Value[T1]    = throw AccessingUndefinedException("tentando avaliar(evaluate) Undefined") 
}

final case class AccessingUndefinedException(msg: String) extends Exception(msg)
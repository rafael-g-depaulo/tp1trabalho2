package value

import types._

import expression.Expression

case class Value[T <: Type](val innerValue: T) extends Expression[T] {
  // def eval[T1 >: T](context: Map[String, Value[Type]]): Value[T1] = this
  def eval(): Value[T] = this.asInstanceOf[Value[T]]
}
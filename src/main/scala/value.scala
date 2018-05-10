package value

import types._

import expression.Expression

case class Value[T <: Type](val innerValue: T) extends Expression[T] {
  // def eval(context: Map[String, Value[Type]]): Value[T] = this
  def eval(): Value[T] = this
}
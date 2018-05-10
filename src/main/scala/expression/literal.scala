package expression
package literal

import types._
import value.Value

class Literal[T <: Type](value: Value[T]) extends Expression[T] {
  // override def eval[T1 <: T](context: Map[String, Value[Type]]): Value[T1] = value
  override def eval(): Value[T] = value.asInstanceOf[Value[T]]
}
package expression
package literal

import types._
import value.Value

class Literal[T <: Type](value: Value[T]) extends Expression[T] {
  // override def eval(context: Map[String, Value[Type]]): Value[T] = value
  override def eval(): Value[T] = value
}
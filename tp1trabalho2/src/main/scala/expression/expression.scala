package expression

import types._
import value.Value
import ed.mutable._

abstract class Expression[T <: Type] {
  // def eval(context: Map[String, Value[Type]]): Value[T]
  def eval(): Value[T]
}
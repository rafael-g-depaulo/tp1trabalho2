package expression

import types._
import value.Value
import ed.mutable._

abstract class Expression[+T <: Type] {
  // def eval[T1 <: T](context: Map[String, Value[Type]]): Value[T1]
  def eval[T1 >: T <: Type](): Value[T1]
}
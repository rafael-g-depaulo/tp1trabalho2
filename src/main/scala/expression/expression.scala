package expression

import types._
import context.Context
import value.Value
import ed.mutable._

abstract class Expression[+T <: Type]() {
  // def eval[T1 <: T](context: Map[String, Value[Type]]): Value[T1]
  def eval[T1 >: T <: Type](context: Context): Value[T1]
}
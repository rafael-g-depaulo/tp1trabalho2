package expression

import types._
import value.Value

abstract class BinExpression[T <: Type](val lhs: Value[T], val rhs: Value[T]) extends Expression[T]
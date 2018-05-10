package expression

import types._
import value.Value

abstract class BinExpression[T <: Type](val lhs: Expression[T], val rhs: Expression[T]) extends Expression[T]
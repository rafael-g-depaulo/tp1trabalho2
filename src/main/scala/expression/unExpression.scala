package expression

import types._
import value.Value

abstract class UnExpression[T <: Type](param: Expression[Type]) extends Expression[T]
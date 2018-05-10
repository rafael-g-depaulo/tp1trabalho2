package expression

import types._
import value.Value

abstract class UnExpression[T <: Type](param: Value[Type]) extends Expression[T]
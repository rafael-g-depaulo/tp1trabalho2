package expression

import types._
import value.Value

abstract class BinExpression[ReturnT <: Type, LT <: Type, RT <: Type](val lhs: Expression[LT], val rhs: Expression[RT]) extends Expression[ReturnT]

package expression

import types._
import value.Value

abstract class UnExpression[ReturnT <: Type, Tparam <: Type](val param: Expression[Tparam]) extends Expression[ReturnT]
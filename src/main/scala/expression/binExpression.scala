package expression

import types._
import context.Context
import value.Value

import scala.reflect.runtime.universe

abstract class BinExpression[ReturnT <: Type, LT <: Type, RT <: Type]
  (val lhs: Expression[LT], val rhs: Expression[RT], retType: universe.Type) extends Expression[ReturnT](retType)

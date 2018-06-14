package expression

import types._
import context.Context
import value.Value

import scala.reflect.runtime.universe

abstract class UnExpression[ReturnT <: Type, Tparam <: Type]
  (val param: Expression[Tparam], retType: universe.Type) extends Expression[ReturnT](retType)

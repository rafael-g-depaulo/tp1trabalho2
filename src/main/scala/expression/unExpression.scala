package expression

import types._
import context.Context
import value.Value

abstract class UnExpression[ReturnT <: Type, Tparam <: Type](val param: Expression[Tparam], _context: Context) extends Expression[ReturnT](_context)

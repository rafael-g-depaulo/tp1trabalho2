package expression

import types._
import context.Context
import value.Value

abstract class BinExpression[ReturnT <: Type, LT <: Type, RT <: Type]
  (val lhs: Expression[LT], val rhs: Expression[RT], _context: Context) extends Expression[ReturnT](_context)

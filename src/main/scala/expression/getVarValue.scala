package expression

import types._
import context.Context
import value.Value
import ed.mutable._

class GetVarValue[+T <: Type](val varName: String) extends Expression[T] {
    def eval[T1 >: T <: Type](context: Context): Value[T1] = context.getVar(varName).asInstanceOf[Value[T1]]
}
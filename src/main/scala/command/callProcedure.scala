package command

import types._
import context.Context
import expression.Expression
import value.Value
import ed.mutable._

class CallProcedure(val procdName: String, val params: (String, Expression[Type])*) extends Command {
  def execute(context: Context) { context.getProcd(procdName).call(context)(params: _*) }
}

object CallProcedure {
    def apply[T <: Type](procdName: String)(params: (String, Value[Type])*): CallProcedure = new CallProcedure(procdName, params: _*)
}
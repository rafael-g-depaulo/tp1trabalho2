package command

import types._
import context.Context
import expression.Expression
import value.Value
import ed.mutable._

import scala.reflect.ClassTag

class CallProcedure(val procdName: String, val params: (String, Expression[Type])*) extends Command {
  def execute(context: Context): Option[Value[Type]] = { context.getProcd(procdName).call(context)(params: _*); None }
}

object CallProcedure {
    def apply[T <: Type](procdName: String)(params: (String, Value[Type])*): CallProcedure = new CallProcedure(procdName, params: _*)
    def apply[T <: Type, C: ClassTag](procdName: String, params: (String, Value[Type])*): CallProcedure = new CallProcedure(procdName, params: _*)
}
package command

import context.Context
import expression._
import value._
import types._

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

class SetVariable[T <: Type](val varName: String, val varValue: Expression[T], ev: TypeTag[T]) extends Command {
  def execute(ctx: Context): Option[Value[Type]] = {
    ctx.setVar[T](varName, varValue.eval(ctx))(ev)
    None
  }
}

object SetVariable {
  def apply[T <: Type](pair: (String, Expression[T]))(implicit ev: TypeTag[T])           : SetVariable[T] = new SetVariable[T](pair._1, pair._2, ev)
  def apply[T <: Type](varName: String, varValue: Expression[T])(implicit ev: TypeTag[T]): SetVariable[T] = new SetVariable[T](varName, varValue, ev)
}
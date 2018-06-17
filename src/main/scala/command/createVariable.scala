package command

import context.Context
import value._
import types._

import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}

class CreateVariable[T <: Type](val varName: String, val varValue: Value[T], ev: TypeTag[T]) extends Command {
  def execute(ctx: Context): Option[Value[Type]] = {
    ctx.createVar[T](varName, varValue)(ev)
    None
  }
}

object CreateVariable {
  def apply[T <: Type](pair: (String, Value[T]))(implicit ev: TypeTag[T]): CreateVariable[T] = new CreateVariable[T](pair._1, pair._2, ev)
  def apply[T <: Type](varName: String, varValue: Value[T])(implicit ev: TypeTag[T]): CreateVariable[T] = new CreateVariable[T](varName, varValue, ev)
}
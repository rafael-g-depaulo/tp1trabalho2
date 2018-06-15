package command

import context.Context
import function._
import value._
import types._

class CreateFunction[T <: Type](val funcName: String, val func: Function[T]) extends Command {
  def execute(ctx: Context): Option[Value[Type]] = {
    ctx.createFunc(funcName, func)
    None
  }
}

object CreateFunction {
  def apply[T <: Type](pair: (String, Function[T])): CreateFunction[T] = new CreateFunction[T](pair._1, pair._2)
  def apply[T <: Type](funcName: String, func: Function[T]): CreateFunction[T] = new CreateFunction[T](funcName, func)
}
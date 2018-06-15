package command

import context.Context
import exceptions._
import types._
import value._

abstract class Command() {
  def execute(ctx: Context): Option[Value[Type]]
}
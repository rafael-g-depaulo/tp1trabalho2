package command

import context.Context

abstract class Command() {
  def execute(ctx: Context): Unit
}
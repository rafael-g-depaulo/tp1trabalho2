package command

import context.Context

abstract class Command(private val context: Context) {
  def execute(): Unit
}
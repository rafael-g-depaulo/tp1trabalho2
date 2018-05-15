package command

import context.Context
import ed._

class Block(private val context: Context, cmds: Command*) {
  private val commands = immutable.List[Command](cmds: _*)
  def execute() { commands foreach { _.execute() } }
}
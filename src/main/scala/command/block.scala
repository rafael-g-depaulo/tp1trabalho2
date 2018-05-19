package command

import context.Context
import ed._

class Block(cmds: Command*) {
  private val commands = immutable.List[Command](cmds: _*)
  def execute(context: Context) { commands foreach { _.execute(context) } }
}
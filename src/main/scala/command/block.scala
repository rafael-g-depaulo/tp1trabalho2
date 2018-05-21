package command

import context.Context
import ed._

class Block(cmds: Command*) {
  val commands = immutable.List[Command](cmds: _*)
  def execute(context: Context) { commands foreach { _.execute(context) } }
}

object Block {
  def apply(cmds: Command*): Block = new Block(cmds: _*)
}
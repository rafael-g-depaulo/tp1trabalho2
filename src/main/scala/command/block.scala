package command

import context.Context
import ed._

case class Block(cmds: Command*) {
  val commands = immutable.List[Command](cmds: _*)
  def execute(context: Context) { commands foreach { _.execute(context) } }
}
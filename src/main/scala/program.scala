package program

import ed.immutable.List
import command._
import context._

case class Program(cmds: List[Command]) {
  val context = new Context()

  def execute() {
    context.addLayer
    cmds foreach { _.execute(context) }
    context.removeLayer
  }
}
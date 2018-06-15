package command

import context.Context
import exceptions._
import value._
import types._
import ed._
import ed.immutable._

case class Block(cmds: Command*) extends Command {
  val commands = immutable.List[Command](cmds: _*)
  def execute(context: Context): Option[Value[Type]] = {
    if (commands.isEmpty)
      return None

    context.addLayer
    val cmdIter = commands.getIterator

    while (cmdIter.hasNext) {
      cmdIter.value.execute(context) match {
        case None => // continua
        case Some(value) => {
          context.removeLayer
          return Some(value)
        }
      }
      cmdIter.next
    }
    cmdIter.value.execute(context) match {
      case None => // continua
      case Some(value) => {
        context.removeLayer
        return Some(value)
      }
    }
    context.removeLayer
    None
  }
}

object Block {
  def apply(cmds: Command*): Block = new Block(cmds: _*)
}
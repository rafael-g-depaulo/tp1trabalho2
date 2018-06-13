package command

import context.Context
import exceptions._
import value._
import types._
import ed._
import ed.immutable._

case class Block(cmds: Command*) extends Command {
  val commands = immutable.List[Command](cmds: _*)
  def execute(context: Context) { commands foreach { _.execute(context) } }
  def execThenReturn(ctx: Context): Value[Type] = {
    def recursiveExecute(b: Block, ctx1: Context): Option[Value[Type]] = {
      def checkAndExecuteCmd(cmdIter: ListIterator[Command]): Option[Value[Type]] = {
        // if is return, return
        if (cmdIter.value.isReturn) {
          val retVal = cmdIter.value.returnValue(ctx)
          ctx.removeLayer()
          return Some(retVal)
        }
        // if is a block, check inside
        if (cmdIter.value.isInstanceOf[Block]) {
          ctx1.addLayer
          val possibleRet = recursiveExecute(cmdIter.value.asInstanceOf[Block], ctx1)
          ctx1.removeLayer

          possibleRet match {
            case Some(value) => return Some(value)
            case None        => // do nothing
          }
        }
        // else, execute command
        else { cmdIter.value.execute(ctx) }
        None
      }

      val cmdIter = b.commands.getIterator()

      // loops through all-but-last commands
      do {
        checkAndExecuteCmd(cmdIter) match {
          case Some(value) => return Some(value)
          case None        => //
        }
        // move on
        if (cmdIter.hasNext) cmdIter.next
      } while (cmdIter.hasNext)

      // checks last command
      return checkAndExecuteCmd(cmdIter)
    }

    recursiveExecute(this, ctx) match {
      case Some(value) => value
      case None        => throw FunctionWithoutReturn("funcao chegou ao fim e nenhum valor foi retornado")
    }
  }
}

object Block {
  def apply(cmds: Command*): Block = new Block(cmds: _*)
}
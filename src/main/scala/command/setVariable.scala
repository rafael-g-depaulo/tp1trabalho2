package command

import context.Context
import value._
import types._

case class SetVariable(val varName: String, val varValue: Value[Type], val context: Context) extends Command(context: Context) {
  def execute() {
    context.setVar(varName, varValue)
  }
}
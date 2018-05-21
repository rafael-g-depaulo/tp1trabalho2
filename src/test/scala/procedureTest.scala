import org.scalatest.{exceptions => _, _}
import ed.exceptions._

import types._
import value._
import command._
import expression._
import exceptions._
import procedure._
import expression.math._
import context.Context

class ProcedureTest extends FlatSpec with Matchers {
  
   val stk: Context = new Context

  "Procedure" should "be able to be created" in {
    val myProcedure = Procedure(
      Block (
        IfThen(GetVarValue("x"), Block())
      )
    )
  }
  it should "not throw an exception when executed with the correct parameters" in {
    val myProcedure = Procedure(
      Block (
        IfThen(GetVarValue("x"), Block())
      )
    )

    stk.addLayer()
    myProcedure.execute(stk, "x" -> Value(TypeBool(true)))
  }
  it should "throw an InexistentVariable exception when executed with missing parameters" in {
    val myProcedure = Procedure(
      Block (
        IfThen(GetVarValue("x"), Block())
      )
    )

    stk.addLayer()
    intercept[InexistentVariable] {
      myProcedure.execute(stk)
    }
  }
}
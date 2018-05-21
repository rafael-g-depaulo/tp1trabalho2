import org.scalatest.{exceptions => _, _}
import ed.exceptions._

import types._
import value._
import command._
import expression._
import expression.comparable._
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
      ), "x"
    )
  }
  it should "not throw an exception when called with the correct parameters" in {
    val myProcedure = Procedure(
      Block (
        IfThen(GetVarValue("x"), Block())
      ), "x"
    )

    stk.addLayer()
    myProcedure.call(stk, "x" -> Value(TypeBool(true)))
    stk.clear()
  }
  it should "throw an IncompleArgumentList exception when called with missing parameters" in {
    val myProcedure = Procedure(
      Block(
        IfThen(GetVarValue("x"), Block())
      ), "x"
    )

    stk.addLayer()
    intercept[IncompleArgumentList] {
      myProcedure.call(stk)
    }

    stk.clear()
  }
  it should "be able to change variables on a deeper layer" in {
    stk.addLayer()
    val myProcedure = Procedure(
      Block(
        IfThenElse(
          EqualInt(GetVarValue("number"), Value(TypeInt(5))),
          Block(
            SetVariable("isNum5" -> Value(TypeBool(true)))
          ),
          Block(
            SetVariable("isNum5" -> Value(TypeBool(false)))
          )
        )
      ),
      "number"
    )

    stk.createVar[TypeBool]("isNum5" -> UndefinedValue)

    GetVarValue("isNum5").eval(stk) should be (UndefinedValue)

    myProcedure.call(stk, "number" -> Value(TypeInt(4)))
    GetVarValue("isNum5").eval(stk) should be (Value(TypeBool(false)))
    
    myProcedure.call(stk, "number" -> Value(TypeInt(5)))
    GetVarValue("isNum5").eval(stk) should be (Value(TypeBool(true)))

    stk.clear()
  }
}
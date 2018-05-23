import org.scalatest.{exceptions => _, _}
import ed.exceptions._

import types._
import value._
import command._
import function._
import expression._
import expression.comparable._
import exceptions._
import procedure._
import expression.math._
import context.Context

class FunctionTest extends FlatSpec with Matchers {
  
   val stk: Context = new Context

  "Function" should "be able to be created" in {
    val add5thenDouble = Function("num")(
      Block (
        Return(
          MultExpression(
            SumExpression(
              GetVarValue("num"),
              Value(TypeInt(5))
            ),
            Value(TypeInt(2))
            )
        )
      )
    )
  }
  it should "throw a WrongParameterName exception when calling with a parameter not listed on the argument list" in {
    val add5thenDouble = Function("num")(
      Block (
        Return(
          MultExpression(
            SumExpression(
              GetVarValue("num"),
              Value(TypeInt(5))
            ),
            Value(TypeInt(2))
            )
        )
      )
    )

    intercept[WrongParameterName] {
      add5thenDouble.call(stk)("num" -> Value(TypeInt(0)), "parametroInexistente" -> Value(TypeBool(false)))
    }

  }
  it should "throw a IncompleArgumentList when calling a function without suppling all of the required parameters" in {
    val add5thenDouble = Function("num")(
      Block (
        Return(
          MultExpression(
            SumExpression(
              GetVarValue("num"),
              Value(TypeInt(5))
            ),
            Value(TypeInt(2))
            )
        )
      )
    )

    intercept[IncompleArgumentList] {
      add5thenDouble.call(stk)()
    }
  }
  it should "work with variables on a deeper layer" in {
    stk.addLayer()
    val add7toXthenReturnX = Function()(
      Block(
        SetVariable(
          "x" -> SumExpression(
            GetVarValue("x"),
            Value(TypeInt(7))
          )
        ),
        Return(GetVarValue("x"))
      )
    )

    stk.createVar("x" -> Value(TypeInt(0)))
    GetVarValue("x").eval(stk) should be (Value(TypeInt(0)))

    add7toXthenReturnX.call(stk)() should be (Value(TypeInt(7)))
    GetVarValue("x").eval(stk) should be (Value(TypeInt(7)))

    add7toXthenReturnX.call(stk)() should be (Value(TypeInt(14)))
    GetVarValue("x").eval(stk) should be (Value(TypeInt(14)))

    stk.clear()
  }
  it should "return a value correctly when used with correct parameters" in {
    val add5thenDouble = Function("num")(
      Block (
        Return(
          MultExpression(
            SumExpression(
              GetVarValue("num"),
              Value(TypeInt(5))
            ),
            Value(TypeInt(2))
            )
        )
      )
    )

    add5thenDouble.call(stk)("num" -> Value(TypeInt(0))) should be (Value(TypeInt(10)))
    add5thenDouble.call(stk)("num" -> Value(TypeInt(1))) should be (Value(TypeInt(12)))
    add5thenDouble.call(stk)("num" -> Value(TypeInt(-5))) should be (Value(TypeInt(0)))
  }
  it should "work when created with the CreateFunction Command, and called with the CallFunction Expression" in {
    stk.addLayer
    CreateFunction("add5thenDouble" -> Function("num")(
      Block (
        Return(
          MultExpression(
            SumExpression(
              GetVarValue("num"),
              Value(TypeInt(5))
            ),
            Value(TypeInt(2))
            )
        )
      )
    )).execute(stk)

    CallFunction("add5thenDouble")("num" -> Value(TypeInt(0))) .eval(stk) should be (Value(TypeInt(10)))
    CallFunction("add5thenDouble")("num" -> Value(TypeInt(1))) .eval(stk) should be (Value(TypeInt(12)))
    CallFunction("add5thenDouble")("num" -> Value(TypeInt(-5))).eval(stk) should be (Value(TypeInt(0)))
  }
}
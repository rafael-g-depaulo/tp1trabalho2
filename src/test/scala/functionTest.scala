import org.scalatest.{exceptions => _, _}
import ed.exceptions._

import types._
import types.ImplicitTyping._
import value._
import command._
import function._
import expression._
import expression.comparable._
import expression.logic._
import exceptions._
import procedure._
import expression.math._
import context.Context

class FunctionTest extends FlatSpec with Matchers {
  
   val stk: Context = new Context

  "Function" should "be able to be created" in {
    val add5thenDouble = Function[TypeInt]("num" -> TypeInt)(
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
    val add5thenDouble = Function[TypeInt]("num" -> TypeInt)(
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
    val add5thenDouble = Function[TypeInt]("num" -> TypeInt)(
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
    val add7toXthenReturnX = Function[TypeInt]()(
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
    val add5thenDouble = Function[TypeInt]("num" -> TypeInt)(
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
    CreateFunction("add5thenDouble" -> Function[TypeInt]("num" -> TypeInt)(
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

    CallFunction[TypeInt]("add5thenDouble")("num" -> Value(TypeInt(0))) .eval(stk) should be (Value(TypeInt(10)))
    CallFunction[TypeInt]("add5thenDouble")("num" -> Value(TypeInt(1))) .eval(stk) should be (Value(TypeInt(12)))
    CallFunction[TypeInt]("add5thenDouble")("num" -> Value(TypeInt(-5))).eval(stk) should be (Value(TypeInt(0)))
    stk.clear
  }
  it should "work when created with a single command instead of a block as a body" in {
    stk.addLayer
    CreateFunction("returnXPlus1", Function[TypeInt](){
        Return(SumExpression(GetVarValue("x"), Value(TypeInt(1))))
      }
    ).execute(stk)

    stk.createVar("x" -> Value(TypeInt(3)))
    
    CallFunction[TypeInt]("returnXPlus1")() .eval(stk) should be (Value(TypeInt(4)))
    stk.clear
  }
  it should "throw a IncompatibleTypeException when called with a parameter of a wrong type" in {
    stk.addLayer
    CreateFunction("add5thenDouble" -> Function[TypeInt]("num" -> TypeInt /*MyImplicitTyping.typeInt2univType(TypeInt)*/)(
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
    CreateFunction("notGate" -> Function[TypeBool]("a" -> TypeBool)(
      Return(
        NotGate(GetVarValue("a"))
      )
    )).execute(stk)

    CallFunction[TypeInt]("add5thenDouble")("num" -> Value(TypeInt(0))) .eval(stk) should be (Value(TypeInt(10)))
    CallFunction[TypeBool]("notGate")("a" -> Value(TypeBool(false)))    .eval(stk) should be (Value(TypeBool(true)))

    intercept[IncompatibleTypeException] {
      CallFunction("add5thenDouble")("num" -> Value(TypeBool(false))).eval(stk)
    }
    intercept[IncompatibleTypeException] {
      CallFunction("notGate")("a" -> Value(TypeInt(0))).eval(stk)
    }

    stk.clear
  }
  it should "work recursively" in {
    stk.addLayer
    CreateFunction("fibonaci" -> Function[TypeInt]("x" -> TypeInt){
      IfThenElse (LessEqual(GetVarValue[TypeInt]("x"), Value(TypeInt(1)))) (
          Return(Value(TypeInt(1)))
        ) (
          Return {
            SumExpression(
              CallFunction[TypeInt]("fibonaci")("x" -> SubExpression(GetVarValue[TypeInt]("x"), Value(TypeInt(1)))),
              CallFunction[TypeInt]("fibonaci")("x" -> SubExpression(GetVarValue[TypeInt]("x"), Value(TypeInt(2))))
            )
          }
        )
    }).execute(stk)

    CreateFunction("!" -> Function[TypeInt]("num" -> TypeInt) (
      IfThenElse (LessEqual(GetVarValue[TypeInt]("num"), Value(TypeInt(1)))) {
        Return(
          Value(TypeInt(1))
        )
      } {
        Return(
          MultExpression(
            GetVarValue[TypeInt]("num"),
            CallFunction[TypeInt]("!")("num" -> SubExpression(GetVarValue[TypeInt]("num"), Value(TypeInt(1))))
          )
        )
      }
    )).execute(stk)
    
    CallFunction[TypeInt]("!")("num" -> Value(TypeInt(0))).eval(stk) should be (Value(TypeInt(1)))
    CallFunction[TypeInt]("!")("num" -> Value(TypeInt(1))).eval(stk) should be (Value(TypeInt(1)))
    CallFunction[TypeInt]("!")("num" -> Value(TypeInt(2))).eval(stk) should be (Value(TypeInt(2)))
    CallFunction[TypeInt]("!")("num" -> Value(TypeInt(3))).eval(stk) should be (Value(TypeInt(6)))
    CallFunction[TypeInt]("!")("num" -> Value(TypeInt(4))).eval(stk) should be (Value(TypeInt(24)))

    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(0))).eval(stk) should be (Value(TypeInt(1)))
    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(1))).eval(stk) should be (Value(TypeInt(1)))
    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(2))).eval(stk) should be (Value(TypeInt(2)))
    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(3))).eval(stk) should be (Value(TypeInt(3)))
    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(4))).eval(stk) should be (Value(TypeInt(5)))
    CallFunction[TypeInt]("fibonaci")("x" -> Value(TypeInt(5))).eval(stk) should be (Value(TypeInt(8)))
    
    stk.clear
  }
}
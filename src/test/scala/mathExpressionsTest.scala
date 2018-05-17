import org.scalatest._
import ed.exceptions._

import types._
import value._
import context.Context
import expression.math._

class MathExpressionsTest extends FlatSpec with Matchers {
  
   val stk: Context = new Context

  "SumExpression" should "properly sum 2 Value[TypeInt]'s together" in {
    SumExpression(
      Value(TypeInt(3)),
      Value(TypeInt(1))
    ).eval(stk) should be (Value(TypeInt(4)))
    
    SumExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval(stk) should be (Value(TypeInt(0)))
    
    SumExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(4))
    ).eval(stk) should be (Value(TypeInt(0)))

    SumExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(7))
    ).eval(stk) should be (Value(TypeInt(3)))
  }

  "SubExpression" should "properly subtract 2 Value[TypeInt]'s together" in {
    SubExpression(
      Value(TypeInt(3)),
      Value(TypeInt(1))
    ).eval(stk) should be (Value(TypeInt(2)))
    
    SubExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval(stk) should be (Value(TypeInt(0)))
    
    SubExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(4))
    ).eval(stk) should be (Value(TypeInt(-8)))

    SubExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(7))
    ).eval(stk) should be (Value(TypeInt(-11)))
  }

  "MultExpression" should "properly multiply 2 Value[TypeInt]'s together" in {
    MultExpression(
      Value(TypeInt(3)),
      Value(TypeInt(2))
    ).eval(stk) should be (Value(TypeInt(6)))
    
    MultExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval(stk) should be (Value(TypeInt(0)))
    
    MultExpression(
      Value(TypeInt(4)),
      Value(TypeInt(0))
    ).eval(stk) should be (Value(TypeInt(0)))

    MultExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(3))
    ).eval(stk) should be (Value(TypeInt(-12)))
  }
  
  "DivExpression" should "properly divide 2 Value[TypeInt]'s together" in {
    DivExpression(
      Value(TypeInt(4)),
      Value(TypeInt(2))
    ).eval(stk) should be (Value(TypeInt(2)))
    
    DivExpression(
      Value(TypeInt(6)),
      Value(TypeInt(1))
    ).eval(stk) should be (Value(TypeInt(6)))
    
    DivExpression(
      Value(TypeInt(0)),
      Value(TypeInt(4))
    ).eval(stk) should be (Value(TypeInt(0)))

    DivExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(2))
    ).eval(stk) should be (Value(TypeInt(-2)))
  }
  it should "throw a NegativeDenomException() when initializing a DivExpression with a denominator of 0" in {
    intercept[NegativeDenomException] {
      DivExpression(
        Value(TypeInt(1)),
        Value(TypeInt(0))
      )
    }
  }
  
  "ModExpression" should "properly take the remainder of 2 Value[TypeInt]'s" in {
    ModExpression(
      Value(TypeInt(4)),
      Value(TypeInt(2))
    ).eval(stk) should be (Value(TypeInt(0)))
    
    ModExpression(
      Value(TypeInt(6)),
      Value(TypeInt(4))
    ).eval(stk) should be (Value(TypeInt(2)))
    
    ModExpression(
      Value(TypeInt(0)),
      Value(TypeInt(4))
    ).eval(stk) should be (Value(TypeInt(0)))

    ModExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(3))
    ).eval(stk) should be (Value(TypeInt(-1)))
  }

  "Math Expressions" should "work with non-literal(non-Value) Expressions as parameters e.g.: 5 % ((8 - 2) / ((2 * 1) + 1)) should be 1" in {
    ModExpression(
      Value(TypeInt(5)),
      DivExpression(
        SubExpression(
          Value(TypeInt(8)),
          Value(TypeInt(2))
        ),
        SumExpression(
          MultExpression(
            Value(TypeInt(2)),
            Value(TypeInt(1))
          ),
          Value(TypeInt(1))
        )
    )
    ).eval(stk) should be (Value(TypeInt(1)))
  }
}
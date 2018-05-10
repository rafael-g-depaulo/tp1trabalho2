import org.scalatest._
import ed.immutable
import ed.exceptions._

import scala.language.implicitConversions

import types._
import expression.math._
import value._
import types.implicitConversionss

class SumExpressionTest extends FlatSpec with Matchers {
  "SumExpression" should "properly sum 2 Value[TypeInt]'s together" in {
    SumExpression(
      Value(TypeInt(3)),
      Value(TypeInt(1))
    ).eval() should be (Value(TypeInt(4)))
    
    SumExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval() should be (Value(TypeInt(0)))
    
    SumExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(4))
    ).eval() should be (Value(TypeInt(0)))

    SumExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(7))
    ).eval() should be (Value(TypeInt(3)))
  }
  it should "work with non-Value Expressions (3 + (2 + 1)) should be 6" in {
    SumExpression(
      Value(TypeInt(3)),
      SumExpression(
        Value(TypeInt(2)),
        Value(TypeInt(1))
      )
    ).eval() should be (Value(TypeInt(6)))
  }
}

class SubExpression extends FlatSpec with Matchers {
  "SubExpression" should "properly subtract 2 Value[TypeInt]'s together" in {
    SubExpression(
      Value(TypeInt(3)),
      Value(TypeInt(1))
    ).eval() should be (Value(TypeInt(2)))
    
    SubExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval() should be (Value(TypeInt(0)))
    
    SubExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(4))
    ).eval() should be (Value(TypeInt(-8)))

    SubExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(7))
    ).eval() should be (Value(TypeInt(-11)))
  }
  it should "work with non-Value Expressions (3 - (4 + 2)) should be -3" in {
    SubExpression(
      Value(TypeInt(3)),
      SumExpression(
        Value(TypeInt(4)),
        Value(TypeInt(2))
      )
    ).eval() should be (Value(TypeInt(-3)))
  }
}

class MultExpression extends FlatSpec with Matchers {
  "MultExpression" should "properly multiply 2 Value[TypeInt]'s together" in {
    MultExpression(
      Value(TypeInt(3)),
      Value(TypeInt(2))
    ).eval() should be (Value(TypeInt(6)))
    
    MultExpression(
      Value(TypeInt(0)),
      Value(TypeInt(0))
    ).eval() should be (Value(TypeInt(0)))
    
    MultExpression(
      Value(TypeInt(4)),
      Value(TypeInt(0))
    ).eval() should be (Value(TypeInt(0)))

    MultExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(3))
    ).eval() should be (Value(TypeInt(-12)))
  }
  it should "work with non-Value Expressions ((2 + 1) * (4 - 2)) should be 6" in {
    MultExpression(
      SumExpression(
        Value(TypeInt(2)),
        Value(TypeInt(1))        
      ),
      SubExpression(
        Value(TypeInt(4)),
        Value(TypeInt(2)) 
      )
    ).eval() should be (Value(TypeInt(6)))
  }
}

class DivExpression extends FlatSpec with Matchers {
  "DivExpression" should "properly divide 2 Value[TypeInt]'s together" in {
    DivExpression(
      Value(TypeInt(4)),
      Value(TypeInt(2))
    ).eval() should be (Value(TypeInt(2)))
    
    DivExpression(
      Value(TypeInt(6)),
      Value(TypeInt(1))
    ).eval() should be (Value(TypeInt(6)))
    
    DivExpression(
      Value(TypeInt(0)),
      Value(TypeInt(4))
    ).eval() should be (Value(TypeInt(0)))

    DivExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(2))
    ).eval() should be (Value(TypeInt(-2)))
  }
  it should "throw a NegativeDenomException() when initializing a DivExpression with a denominator of 0" in {
    intercept[NegativeDenomException] {
      DivExpression(
        Value(TypeInt(1)),
        Value(TypeInt(0))
      )
    }
  }
  it should "work with non-Value Expressions (8 - 2) / ((2 * 1) + 1) should be 2" in {
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
    ).eval() should be (Value(TypeInt(2)))
  }
}

class ModExpression extends FlatSpec with Matchers {
  "ModExpression" should "properly take the remainder of 2 Value[TypeInt]'s" in {
    ModExpression(
      Value(TypeInt(4)),
      Value(TypeInt(2))
    ).eval() should be (Value(TypeInt(0)))
    
    ModExpression(
      Value(TypeInt(6)),
      Value(TypeInt(4))
    ).eval() should be (Value(TypeInt(2)))
    
    ModExpression(
      Value(TypeInt(0)),
      Value(TypeInt(4))
    ).eval() should be (Value(TypeInt(0)))

    ModExpression(
      Value(TypeInt(-4)),
      Value(TypeInt(3))
    ).eval() should be (Value(TypeInt(-1)))
  }
  it should "work with non-Value Expressions (5 % (8 - 2) / ((2 * 1) + 1)) should be 1" in {
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
    ).eval() should be (Value(TypeInt(1)))
  }
}
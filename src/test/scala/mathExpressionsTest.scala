import org.scalatest._
import br.unb.cic.ed.immutable

import br.unb.cic.ed.exceptions._
import scala.language.implicitConversions

import types._
import expression.math._
import value._
import types.implicitConversionss

class SumExpression  extends FlatSpec with Matchers {
    "A sum expression" should "result in Value(TypeInt(4)) when calling SumExpression(Value(TypeInt(3)), Value(TypeInt(1)))" in {
        // val val3a: TypeInt = implicitConversionss.int2TypeInt(3)
        // val val3b: TypeInt = 3 
        SumExpression(Value(TypeInt(3)), Value(TypeInt(1))).eval() should be (Value(TypeInt(4)))
    }
}
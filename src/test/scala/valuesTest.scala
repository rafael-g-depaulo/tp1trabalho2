import org.scalatest._
import br.unb.cic.ed.immutable

import br.unb.cic.ed.exceptions._
import scala.language.implicitConversions

import types._
import expression.math._
import value._
import types.implicitConversionss

class ValueTypeIntTest extends FlatSpec with Matchers {
    "Value[TypeInt]" should "be able to be initialized with a TypeInt without any exception being thrown" in {
      val valueNeg4: Value[TypeInt] = Value(TypeInt(-4))
      val value0: Value[TypeInt]    = Value(TypeInt(0))
      val value6: Value[TypeInt]    = Value(TypeInt(6))
    }
    it should "return the inner Int when calling innerValue.apply()" in {
      Value(TypeInt(-4)).innerValue.apply() should be (-4)
      Value(TypeInt(0)) .innerValue.apply() should be (0)
      Value(TypeInt(6)) .innerValue.apply() should be (6)
      
      Value(TypeInt(-4)).innerValue() should be (-4)
      Value(TypeInt(0)) .innerValue() should be (0)
      Value(TypeInt(6)) .innerValue() should be (6)
    }
    it should "return itself when calling eval() method" in {
      
      Value(TypeInt(-4)).eval() should be (Value(TypeInt(-4)))
      Value(TypeInt(0)) .eval() should be (Value(TypeInt(0)))
      Value(TypeInt(6)) .eval() should be (Value(TypeInt(6)))
    }
}

class ValueTypeBoolTest extends FlatSpec with Matchers {
    "Value[TypeBool]" should "be able to be initialized with a TypeBool without any exception being thrown" in {
      val valueFalse: Value[TypeBool] = Value(TypeBool(false))
      val valueTrue:  Value[TypeBool] = Value(TypeBool(true))
    }
    it should "return the inner Boolean when calling innerValue.apply()" in {
      Value(TypeBool(false)).innerValue.apply() should be (false)
      Value(TypeBool(true)) .innerValue.apply() should be (true)
      
      Value(TypeBool(false)).innerValue() should be (false)
      Value(TypeBool(true)) .innerValue() should be (true)
    }
    it should "return itself when calling eval() method" in {
      Value(TypeBool(false)).eval() should be (Value(TypeBool(false)))
      Value(TypeBool(true)) .eval() should be (Value(TypeBool(true)))
    }
}
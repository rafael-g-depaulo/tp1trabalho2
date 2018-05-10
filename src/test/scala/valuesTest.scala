import org.scalatest._
import ed.immutable

import ed.exceptions._
import scala.language.implicitConversions

import types._
import expression.math._
import value._
import types.implicitConversionss

class ValueTypeIntTest extends FlatSpec with Matchers {
    "Value[TypeInt]" should "be able to be initialized with a TypeInt without any exception being thrown" in {
      val valueNeg4: Value[TypeInt] = Value(TypeInt(-4))
      val value0:    Value[TypeInt] = Value(TypeInt(0))
      val value6:    Value[TypeInt] = Value(TypeInt(6))
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

class UndefinedValueTest extends FlatSpec with Matchers {
  "UndefinedValue" should "be able to be inputted as a value to any val/var that has a type Value[T <: Type]" in {
    val intValue:  Value[TypeInt]  = UndefinedValue
    val boolValue: Value[TypeBool] = UndefinedValue
  }
  it should "be able to be overriden when inputted as a value to any val/var that has a type Value[T <: Type]" in {
    var intValue:  Value[TypeInt]  = UndefinedValue
    var boolValue: Value[TypeBool] = UndefinedValue

    intValue = Value(TypeInt(5))
    boolValue = Value(TypeBool(true))

    intValue  should be (Value(TypeInt(5)))
    boolValue should be (Value(TypeBool(true)))
  }
  it should "not compile and give a type mismatch error when overriding with a wrong Value[T] after initializing a var of type Value[T] with UndefinedValue" in {
    var intValue:  Value[TypeInt]  = UndefinedValue
    var boolValue: Value[TypeBool] = UndefinedValue
 
    // boolValue = Value(TypeInt(5))        // Essas duas linhas de código causam erro
    // intValue = Value(TypeBool(true))     // "type mismatch" se forem descomentadas
  }
  it should "throw an AccessingUndefinedException when trying to evaluate an Undefined value, or access it's innerValue" in {
    val intValue:  Value[TypeInt]  = UndefinedValue
    val boolValue: Value[TypeBool] = UndefinedValue

    intercept[AccessingUndefinedException] {
      intValue.innerValue()
    }

    intercept[AccessingUndefinedException] {
      boolValue.innerValue()
    }

    intercept[AccessingUndefinedException] {
      intValue.eval()
    }

    intercept[AccessingUndefinedException] {
      boolValue.eval()
    }
  }
}
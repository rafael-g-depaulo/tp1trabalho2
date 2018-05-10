import org.scalatest._
import ed.immutable

import ed.exceptions._
import scala.language.implicitConversions

import types._
import expression.math._
import value._
import types.implicitConversionss

class TypeIntTest extends FlatSpec with Matchers {
    "TypeInt" should "be able to be initialized with a Int without any exception being thrown" in {
      val typeIntNeg7 = TypeInt(-7)
      val typeInt0    = TypeInt(0)
      val typeInt5    = TypeInt(5) 
    }
    it should "return the inner Int when calling apply method" in {
      TypeInt(-3).apply() should be (-3)
      TypeInt(0) .apply() should be (0)
      TypeInt(8) .apply() should be (8)

      TypeInt(-7)() should be (-7)
      TypeInt(0) () should be (0)
      TypeInt(5) () should be (5)
    }
}

class TypeBoolTest extends FlatSpec with Matchers {
    "TypeInt" should "be able to be initialized with a Boolean without any exception being thrown" in {
      val typeBoolFalse = TypeBool(false)
      val typeBoolTrue  = TypeBool(true)
    }
    it should "return the inner Boolean when calling apply method" in {
      TypeBool(false).apply() should be (false)
      TypeBool(true) .apply() should be (true)

      TypeBool(false)() should be (false)
      TypeBool(true) () should be (true)
    }
}
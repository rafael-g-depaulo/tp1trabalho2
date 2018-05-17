import org.scalatest._
import ed.immutable
import ed.exceptions._

import types._
import value._
import context._
import expression.logic._


class LogicGatesTest extends FlatSpec with Matchers {

   val stk: Context = new Context

  "AND gate" should "work as an AND gate with literal values" in {
    // false && false == false
    AndGate(
      Value(TypeBool(false)),
      Value(TypeBool(false))
    ).eval(stk) should be (Value(TypeBool(false)))
    
    // false && true == false
    AndGate(
      Value(TypeBool(false)),
      Value(TypeBool(true))
    ).eval(stk) should be (Value(TypeBool(false)))
    
    // true && false == false
    AndGate(
      Value(TypeBool(true)),
      Value(TypeBool(false))
    ).eval(stk) should be (Value(TypeBool(false)))
    
    // true && true == true
    AndGate(
      Value(TypeBool(true)),
      Value(TypeBool(true))
    ).eval(stk) should be (Value(TypeBool(true)))
  }
  
  "OR gate" should "work as an OR gate with literal values" in {
    // false || false == false
    OrGate(
      Value(TypeBool(false)),
      Value(TypeBool(false))
    ).eval(stk) should be (Value(TypeBool(false)))
    
    // false || true == true
    OrGate(
      Value(TypeBool(false)),
      Value(TypeBool(true))
    ).eval(stk) should be (Value(TypeBool(true)))
    
    // true && false == true
    OrGate(
      Value(TypeBool(true)),
      Value(TypeBool(false))
    ).eval(stk) should be (Value(TypeBool(true)))
    
    // true && true == true
    OrGate(
      Value(TypeBool(true)),
      Value(TypeBool(true))
    ).eval(stk) should be (Value(TypeBool(true)))
  }
  
  "NOT gate" should "work as a NOT gate" in {
    // !false == true
    NotGate(
      Value(TypeBool(false))
    ).eval(stk) should be (Value(TypeBool(true)))
    
    // !true == false
    NotGate(
      Value(TypeBool(true))
    ).eval(stk) should be (Value(TypeBool(false)))
  }
}
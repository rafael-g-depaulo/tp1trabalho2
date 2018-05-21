import org.scalatest._
import ed.immutable
import ed.exceptions._

import types._
import value._
import context.Context
import expression.comparable._
import expression.math._
import expression.logic._


class ComparableTest extends FlatSpec with Matchers {

	behavior of "'LessThan' comparison "

	val stk = new Context 

		it should "Return true when comparing the expressions Sum (3+1) and TypeInt 5" in {
			LessThan(
				SumExpression(Value(TypeInt(3)), Value(TypeInt(1))),
				Value(TypeInt(5))
			).eval(stk) should be (Value(TypeBool(true)))
		}	

			it should "Return false when comparing the expressions TypeTnt 3 and 3" in {
			LessThan(
				Value(TypeInt(3)),
				Value(TypeInt(3))
			).eval(stk) should be (Value(TypeBool(false)))
		}	

			it should "Return true when comparing the expressions TypeTnt -5 and -3" in {
			LessThan(
				Value(TypeInt(-5)),
				Value(TypeInt(-3))
			).eval(stk) should be (Value(TypeBool(true)))
		}	

	behavior of "'LessEqual' comparison"

		it should "Return false when comparing the expressions Mult (2*2) and TypeInt 2" in {
			LessEqual(
				MultExpression(Value(TypeInt(2)), Value(TypeInt(2))),
				Value(TypeInt(2))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return true when comparing the expressions TypeInt 4 and 4" in {
			LessEqual(
				Value(TypeInt(4)),
				Value(TypeInt(4))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return true when comparing the expressions TypeInt -4 and 2" in {
			LessEqual(
				Value(TypeInt(-4)),
				Value(TypeInt(2))
			).eval(stk) should be (Value(TypeBool(true)))
		}	

	behavior of "'GreaterThan' comparision"
	
		it should "Return false when comparing the expressions Sub (3-3) and TypeInt 4" in {
			GreaterThan(
				SubExpression(Value(TypeInt(3)), Value(TypeInt(3))),
				Value(TypeInt(4))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return false when comparing the expressions TypeInt 0 and 0" in {
			GreaterThan(
				Value(TypeInt(0)),
				Value(TypeInt(0))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return false when comparing the expressions TypeInt -4 and -2" in {
			GreaterThan(
				Value(TypeInt(-4)),
				Value(TypeInt(-2))
			).eval(stk) should be (Value(TypeBool(false)))
		}

	behavior of "'GreaterEqual' comparison"

		it should "Return false when comparing the expressions Div (10/10) and TypeInt 3" in {
			GreaterEqual(
				DivExpression(Value(TypeInt(10)), Value(TypeInt(10))),
				Value(TypeInt(3))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return true when comparing the expressions TypeInt 2 and 2" in {
			GreaterEqual(
				Value(TypeInt(2)),
				Value(TypeInt(2))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return true when comparing the expressions TypeInt -4 and -7" in {
			GreaterEqual(
				Value(TypeInt(-4)),
				Value(TypeInt(-7))
			).eval(stk) should be (Value(TypeBool(true)))
		}	

	behavior of "'Equal (Int)' comparision"
	
		it should "Return false when comparing the expressions Sub (5-3) and TypeInt 1" in {
			EqualInt(
				SubExpression(Value(TypeInt(5)), Value(TypeInt(3))),
				Value(TypeInt(1))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return true when comparing the expressions TypeInt 4 and 4" in {
			EqualInt(
				Value(TypeInt(4)),
				Value(TypeInt(4))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return true when comparing the expressions TypeInt -2 and -2" in {
			EqualInt(
				Value(TypeInt(-2)),
				Value(TypeInt(-2))
			).eval(stk) should be (Value(TypeBool(true)))
		}

	behavior of "'Equal (Bool)' comparision"
	
		it should "Return false when comparing the expressions AndGate (true && true) and TypeBool false" in {
			EqualBool(
				AndGate(Value(TypeBool(true)), Value(TypeBool(true))),
				Value(TypeBool(false))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return true when comparing the expressions OrGate (false || false) and TypeBool false" in {
			EqualBool(
				OrGate(Value(TypeBool(false)), Value(TypeBool(false))),
				Value(TypeBool(false))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return false when comparing the expressions TypeInt true and NotGate(false)" in {
			EqualBool(
				Value(TypeBool(true)),
				NotGate(Value(TypeBool(false)))
			).eval(stk) should be (Value(TypeBool(true)))
		}

	behavior of "'Different (Int)' comparision"
	
		it should "Return true when comparing the expressions Sub (5-3) and TypeInt 1" in {
			DifferentInt(
				SubExpression(Value(TypeInt(5)), Value(TypeInt(3))),
				Value(TypeInt(1))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return false when comparing the expressions TypeInt 4 and 4" in {
			DifferentInt(
				Value(TypeInt(4)),
				Value(TypeInt(4))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return false when comparing the expressions TypeInt -2 and -2" in {
			DifferentInt(
				Value(TypeInt(-2)),
				Value(TypeInt(-2))
			).eval(stk) should be (Value(TypeBool(false)))
		}

	behavior of "'Different (Bool)' comparision"
	
		it should "Return true when comparing the expressions AndGate (true && true) and TypeBool false" in {
			DifferentBool(
				AndGate(Value(TypeBool(true)), Value(TypeBool(true))),
				Value(TypeBool(false))
			).eval(stk) should be (Value(TypeBool(true)))
		}

		it should "Return false when comparing the expressions OrGate (false || false) and TypeBool false" in {
			DifferentBool(
				OrGate(Value(TypeBool(false)), Value(TypeBool(false))),
				Value(TypeBool(false))
			).eval(stk) should be (Value(TypeBool(false)))
		}

		it should "Return false when comparing the expressions TypeInt true and NotGate(false)" in {
			DifferentBool(
				Value(TypeBool(true)),
				NotGate(Value(TypeBool(false)))
			).eval(stk) should be (Value(TypeBool(false)))
		}

}

import org.scalatest.{exceptions => _, _}
import ed.exceptions._

import types._
import value._
import context._
import exceptions.InexistentVariable
import expression._
import command._

class ContextTest extends FlatSpec with Matchers {
  "the Context" should "be able to have new layers added onto it" in {
    val context = new Context

    context.layerCount should be (0)
    context.addLayer
    context.layerCount should be (1)
    context.addLayer
    context.addLayer
    context.layerCount should be (3)        
  }
  it should "be able to have layers removed from it" in {
    val context = new Context
    
    context.layerCount should be (0)
    context.addLayer
    context.layerCount should be (1)
    context.addLayer
    context.layerCount should be (2)
    context.removeLayer            
    context.layerCount should be (1)
    context.removeLayer            
    context.layerCount should be (0)
  }
  it should "throw a StackUnderFlowException when calling removeLayer with an empty context" in {
    val context = new Context
    
    context.layerCount should be (0)
    context.addLayer
    context.layerCount should be (1)
    context.removeLayer            
    context.layerCount should be (0)
    intercept[StackUnderflowException] {
      context.removeLayer            
    }
  }
  it should "throw a EmptyContextException when setting a variable in an empty context" in {
    val context = new Context
    context.stack.isEmpty should be (true)
    intercept[EmptyContextException] {
      context.setVar("x", Value(TypeInt(4)))
    } 
  }
  it should "be able to have variables set to and got from the top layer" in {
    val context = new Context
    context.addLayer

    context.setVar("intVar", Value(TypeInt(4)))
    context.setVar("boolVar" -> Value(TypeBool(true)))

    context.getVar("intVar") should be (Value(TypeInt(4)))
    context.getVar("boolVar") should be (Value(TypeBool(true)))
  }
  it should "be able to have variables initialized with UndefinedValue" in {
    val context = new Context
    context.addLayer

    context.setVar("undefined", UndefinedValue)
    context.getVar("undefined") should be (UndefinedValue)
  }
  it should "throw InexistentVariable exception when trying to use getVar method with a string that doesn't correspond to any variable names" in {
    val context = new Context
    context.addLayer

    context.setVar("5" -> Value(TypeInt(5)))

    intercept[InexistentVariable] {
      context.getVar("4")
    }
  }
  it should "be able to \"getVar\" a variable from a deeper layer" in {
    val context = new Context
    context.addLayer

    context.setVar("intVar" -> Value(TypeInt(5)))
    context.addLayer
    context.addLayer

    context.getVar("intVar") should be (Value(TypeInt(5)))

  }
  it should "throw a InvalidVariableName exception when setting 2 variables in the same layer with the same name" in {
    val context = new Context
    context.addLayer
    context.setVar("myVar" -> Value(TypeBool(true)))
    intercept[InvalidVariableName] {
      context.setVar("myVar" -> Value(TypeBool(true)))
    }
  }
  it should "return the newest layer's variable when searching for a variable whose name is shared with another variable in a deeper layer" in {
    val context = new Context
    context.addLayer
    context.setVar("myBoolean" -> Value(TypeBool(true)))
    context.addLayer
    context.setVar("myBoolean" -> Value(TypeBool(false)))

    context.getVar("myBoolean") should be (Value(TypeBool(false)))
  }

  it should "work with the GetVarValue Expression and SetVariable Command" in {
    val context = new Context
    context.addLayer

    // checando que a variável não existia antes
    intercept[InexistentVariable] {
      context.getVar("x")
    }
    SetVariable("x", Value(TypeInt(5))).execute(context)
    
    // a expressão GetVarValue deve jogar uma exceção se avaliada em um contexto sem a variável
    intercept[InexistentVariable] {
      val ctx = new Context
      ctx.addLayer
      GetVarValue("x").eval(ctx)
    }

    // usando o contexto que contém uma variavel com o mesmo nome, o valor correto é retornado ao avaliar a Expressão
    GetVarValue("x").eval(context) should be (Value(TypeInt(5)))
  }
}
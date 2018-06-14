// import org.scalatest.{exceptions => _, _}
// import ed.exceptions._

// import types._
// import value._
// import function._
// import procedure._
// import context._
// import exceptions.{InexistentThing, InvalidName}
// import expression._
// import expression.math._
// import expression.logic._
// import command._

// class ContextTest extends FlatSpec with Matchers {
//   "the Context" should "be able to have new layers added onto it" in {
//     val context = new Context

//     context.layerCount should be (0)
//     context.addLayer
//     context.layerCount should be (1)
//     context.addLayer
//     context.addLayer
//     context.layerCount should be (3)        
//   }
//   it should "be able to have layers removed from it" in {
//     val context = new Context
    
//     context.layerCount should be (0)
//     context.addLayer
//     context.layerCount should be (1)
//     context.addLayer
//     context.layerCount should be (2)
//     context.removeLayer            
//     context.layerCount should be (1)
//     context.removeLayer            
//     context.layerCount should be (0)
//   }
//   it should "throw a StackUnderFlowException when calling removeLayer with an empty context" in {
//     val context = new Context
    
//     context.layerCount should be (0)
//     context.addLayer
//     context.layerCount should be (1)
//     context.removeLayer            
//     context.layerCount should be (0)
//     intercept[StackUnderflowException] {
//       context.removeLayer            
//     }
//   }
//   it should "throw a EmptyContextException when creating a variable in an empty context" in {
//     val context = new Context
//     context.stack.isEmpty should be (true)
//     intercept[EmptyContextException] {
//       context.createVar("x", Value(TypeInt(4)))
//     } 
//   }
//   it should "be able to have variables created, set to and got from the top layer" in {
//     val context = new Context
//     context.addLayer

//     context.createVar[TypeInt]("intVar", UndefinedValue)
//     context.createVar("boolVar" -> Value(TypeBool(false)))

//     context.setVar("intVar", Value(TypeInt(4)))
//     context.setVar("boolVar" -> Value(TypeBool(true)))

//     context.getVar("intVar") should be (Value(TypeInt(4)))
//     context.getVar("boolVar") should be (Value(TypeBool(true)))
//   }
  
  
//   it should "be able to have functions created, and got from the top layer" in {
//     val context = new Context
//     context.addLayer

//     context.createFunc("add1", Function(TypeInt.getType)("num" -> TypeInt.getType)(Block(
//       Return(
//         SumExpression(
//           GetVarValue("num"),
//           Value(TypeInt(1))
//         )
//       )
//     )))
//     context.createFunc("notGate" -> Function(TypeBool.getType)("x" -> TypeBool.getType)(
//       Block(
//         Return(
//           NotGate(GetVarValue("x"))
//         )
//       )
//     ))

//     context.getFunc("add1")   .call(context)("num" -> Value(TypeInt(5)))   should be (Value(TypeInt(6)))
//     context.getFunc("notGate").call(context)("x" -> Value(TypeBool(true))) should be (Value(TypeBool(false)))
//   }

//   it should "be able to have procedures created, and got from the top layer" in {
//     val context = new Context
//     context.addLayer
//     context.createVar("x" -> Value(TypeBool(false)))

//     context.createProcd("myProc" -> Procedure()(Block(SetVariable("x" -> Value(TypeBool(true))))))
//     context.getProcd("myProc").call(context)()

//     context.getVar("x") should be (Value(TypeBool(true)))
//   }

//   it should "be able to have variables initialized with UndefinedValue" in {
//     val context = new Context
//     context.addLayer

//     context.createVar("undefined", UndefinedValue)
//     context.getVar("undefined") should be (UndefinedValue)
//   }
//   it should "throw InexistentThing exception when trying to use getVar/getFunc/getProcd method with a string that doesn't correspond to any variable/function/procedure names" in {
//     val context = new Context
//     context.addLayer

//     context.createVar  ("5" -> Value(TypeInt(5)))
//     context.createFunc ("5"  -> Function(TypeInt.getType)()(Block(Return(Value(TypeInt(5))))))
//     context.createProcd("5" -> Procedure(Block()))

//     intercept[InexistentThing] {
//       context.getVar("4")
//     }
//     intercept[InexistentThing] {
//       context.getFunc("4")
//     }
//     intercept[InexistentThing] {
//       context.getProcd("4")
//     }
//   }
//   it should "be able to \"getVar\" a variable from a deeper layer" in {
//     val context = new Context
//     context.addLayer

//     context.createVar("intVar" -> Value(TypeInt(5)))
//     context.addLayer
//     context.addLayer

//     context.getVar("intVar") should be (Value(TypeInt(5)))

//   }
//   it should "throw a InvalidName exception when creating 2 variables, functions or procedures in the same layer with the same name" in {
//     val context = new Context
//     context.addLayer

//     context.createVar  ("myVar"  -> Value(TypeBool(true)))
//     context.createFunc ("myFoo"  -> Function(TypeInt.getType)()(Block(Return(Value(TypeInt(0))))))
//     context.createProcd("myProc" -> Procedure(Block()))

//     intercept[InvalidName] {
//       context.createVar("myVar" -> Value(TypeBool(true)))
//     }
//     intercept[InvalidName] {
//       context.createFunc("myFoo" -> Function(TypeInt.getType)()(Block(Return(Value(TypeInt(0))))))
//     }
//     intercept[InvalidName] {
//       context.createProcd("myProc" -> Procedure(Block()))
//     }
    
//   }
//   it should "return the newest layer's variable when searching for a variable whose name is shared with another variable in a deeper layer" in {
//     val context = new Context
//     context.addLayer
//     context.createVar("myBoolean" -> Value(TypeBool(true)))
//     context.addLayer
//     context.createVar("myBoolean" -> Value(TypeBool(false)))

//     context.getVar("myBoolean") should be (Value(TypeBool(false)))
//   }

//   it should "work with the GetVarValue Expression, and the CreateVariable and SetVariable Commands" in {
//     val context = new Context
//     context.addLayer

//     // checando que a variável não existia antes
//     intercept[InexistentThing] {
//       context.getVar("x")
//     }
//     CreateVariable("x", Value(TypeInt(5))).execute(context)
    
//     // a expressão GetVarValue deve jogar uma exceção se avaliada em um contexto sem a variável
//     intercept[InexistentThing] {
//       val ctx = new Context
//       ctx.addLayer
//       GetVarValue("x").eval(ctx)
//     }

//     // usando o contexto que contém uma variavel com o mesmo nome, o valor correto é retornado ao avaliar a Expressão
//     GetVarValue("x").eval(context) should be (Value(TypeInt(5)))

//     // e mesmo estando em outra camada, podemos alterar o valor de x
//     SetVariable("x" -> Value(TypeInt(10))).execute(context)
//     GetVarValue("x").eval(context) should be (Value(TypeInt(10)))
//   }
// }
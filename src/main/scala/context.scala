package context

import ed.mutable._
import ed.immutable._
import ed.contracts._
import exceptions._
import expression._
import procedure._
import function._
import value._
import types._

import scala.reflect.runtime.universe.{TypeTag, typeTag}
import scala.reflect.runtime.universe

class Context {
  var stack      = ed.immutable.List[Map[String, Value[Type]]]()
  var procdStack = ed.immutable.List[Map[String, Procedure]]()
  var funcStack  = ed.immutable.List[Map[String, Function[Type]]]()

  def addLayer() {
    stack      = HashTable[String, Value[Type]](50) :: stack
    procdStack = HashTable[String, Procedure](50) :: procdStack
    funcStack  = HashTable[String, Function[Type]](50) :: funcStack
  }

  def layerCount(): Int = stack.size

  def removeLayer() {
    if (!stack.isEmpty) {
      stack      = stack.tail
      procdStack = procdStack.tail
      funcStack  = funcStack.tail
    }
    else 
      throw StackUnderflowException("Undeflow no Stack de contexto")
  }

  def getVar[T <: Type](varName: String): Value[T] = {
    getThing[Value[Type]](varName, stack, "variavel").asInstanceOf[Value[T]]
  }

  def getFunc[T <: Type](funcName: String): Function[T] = getThing[Function[Type]](funcName, funcStack, "funcao").asInstanceOf[Function[T]]
  def getProcd(procdName: String): Procedure            = getThing[Procedure](procdName, procdStack, "procedure")

  def createVar[T <: Type](pair: (String, Expression[T])) { createVar(pair._1, pair._2) }
  def createVar[T <: Type](name: String, value: Expression[T] = UndefinedValue) {
    createThing[Value[T]](name, value.eval(this), stack.asInstanceOf[ed.immutable.List[ed.contracts.Map[String, Value[T]]]], "Variavel")
  }
  
  def createFunc(pair: (String, Function[Type])) { createFunc(pair._1, pair._2) }
  def createFunc(name: String, func: Function[Type]) {
    createThing[Function[Type]](name, func, funcStack, "Funcao")
  }

  def createProcd(pair: (String, Procedure)) { createProcd(pair._1, pair._2) }
  def createProcd(name: String, procd: Procedure) {
    createThing[Procedure](name, procd, procdStack, "Procedure")
  }

  // funcao privada que coloca alguma coisa no stack correspondente
  private def createThing[Thing](name: String, thing: Thing, thingStack: ed.immutable.List[Map[String, Thing]], thingTypeName: String) {
    if (thingStack.isEmpty)
      throw EmptyContextException("Contexto vazio")
    else if (thingStack.head.hasKey(name))
      throw InvalidName(thingTypeName+" de nome repetido criada")
    else
      thingStack.head.insert(name -> thing)
  }

  // funcao privada que recupera alguma coisa do stack correspondente
  private def getThing[Thing](name: String, thingStack: ed.immutable.List[Map[String, Thing]], thingTypeName: String): Thing = {
    
    // se o stack esta vazio
    if (thingStack.isEmpty)
      throw EmptyContextException("Contexto vazio")
    else {
      val ite = thingStack.getIterator()

      // itera em todas as camadas de contexto até achar a mais interna que contenha uma coisa com aquele nome
      while (!ite.value.hasKey(name) && ite.hasNext)
        ite.next()

      // retorna o valor da coisa, ou joga uma exceção, se ela não existe
      ite.value.get(name) match {
        case Some(value) => value
        case None        => throw InexistentThing("Tentando pegar valor de "+ thingTypeName +" não existente")
      }     
    }
  }

  def setVar(pair: (String, Expression[Type])) { setVar(pair._1, pair._2) }
  def setVar(name: String, value: Expression[Type]) {
    if (stack.isEmpty)
      throw EmptyContextException("Contexto vazio")
    
    // checa no stack inteiro pra achar a variável
    val myIte = stack.getIterator
    while (myIte.hasNext) {
      if (myIte.value.hasKey(name)) {
        myIte.value.insert(name -> value.eval(this))
        return
      }
      else myIte.next()
    }

    if (myIte.value.hasKey(name))
      myIte.value.insert(name -> value.eval(this))
    // checou no stack inteiro e não achou.
    else
      throw InexistentThing("tentando mudar valor de variável não criada")
  }
  
  def clear() {
    while (!stack.isEmpty) removeLayer
  }
}
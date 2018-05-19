package context

import ed.mutable._
import ed.immutable._
import ed.contracts._
import value._
import types._

class Context {
  var stack = ed.immutable.List[Map[String, Value[Type]]]()

  def addLayer() {
    stack = HashTable[String, Value[Type]](500) :: stack
  }

  def layerCount(): Int = stack.size

  def removeLayer() {
    if (!stack.isEmpty)
      stack = stack.tail
    else 
      throw StackUnderflowException("Undeflow no Stack de contexto")
  }

  def getVar(varName: String): Value[Type] = {
    // se o stack esta vazio
    if (stack.isEmpty)
      throw EmptyContextException("Contexto vazio")
    else {
      val ite = stack.getIterator()

      // itera em todas as camadas de contexto até achar a mais interna que contenha uma variável com aquele nome
      while (!ite.value.hasKey(varName) && ite.hasNext)
        ite.next()

      // retorna o valor da variável, ou joga uma exceção, se ela não existe
      ite.value.get(varName) match {
        case Some(value) => value
        case None        => throw InexistentVariableName("Tentando pegar Valor de variavel não existente")
      }     
    }
  }

  def setVar(pair: (String, Value[Type])) { setVar(pair._1, pair._2) }
  def setVar(name: String, value: Value[Type]) {
    if (stack.isEmpty)
      throw EmptyContextException("Contexto vazio")
    else if (stack.head.hasKey(name))
      throw InvalidVariableName("Variavel de nome repetido criada")
      stack.head.insert(name -> value)
  }
}

final case class InvalidVariableName(msg: String) extends Exception(msg)
final case class InexistentVariableName(msg: String) extends Exception(msg)
final case class StackUnderflowException(msg: String) extends Exception(msg)
final case class EmptyContextException(msg: String) extends Exception(msg)
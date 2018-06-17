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
import scala.reflect.ClassTag
import scala.reflect.runtime.universe

class Context {
  var stack      = ed.immutable.List[Map[String, Tuple2[Value[Type], universe.Type]]]()
  var procdStack = ed.immutable.List[Map[String, Procedure]]()
  var funcStack  = ed.immutable.List[Map[String, Function[Type]]]()

  def addLayer() {
    stack      = HashTable[String, Tuple2[Value[Type], universe.Type]](50) :: stack
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

  def getVar[T <: Type](varName: String)(implicit ev: TypeTag[T]): Value[T] = getVar[T](ev.tpe)(varName)
  def getVar[T <: Type](ut: universe.Type)(varName: String): Value[T] = {
    val varVal = getThing[Tuple2[Value[Type], universe.Type]](varName, stack, "variavel")

    // checando tipo
    if (varVal._2 =:= ut) varVal._1.asInstanceOf[Value[T]]
    else throw IncompatibleTypeException("tentando interpretar a variavel \""+varVal._1 + "("+varVal._2+")\" como sendo \""+ut+"\"")// uma de outro tipo.")
    
  }

  def getFunc[T <: Type](funcName: String): Function[T] = getThing[Function[Type]](funcName, funcStack, "funcao").asInstanceOf[Function[T]]
  def getProcd(procdName: String): Procedure            = getThing[Procedure](procdName, procdStack, "procedure")

  def createVar[T <: Type](pair: (String, Expression[T]))(implicit ev: TypeTag[T]) { createVar[T](ev.tpe)(pair._1, pair._2) }
  def createVar[T <: Type](name: String, value: Expression[T] = UndefinedValue)(implicit ev: TypeTag[T]) { createVar[T](ev.tpe)(name, value) }
  def createVar[T <: Type](ev: universe.Type)(name: String, value: Expression[T]) {

  // def createVar[T <: Type](pair: (String, Expression[T]))(ev: Either[TypeTag[T], universe.Type]) { createVar[T, Int](pair._1, pair._2)(ev) }
  // def createVar[T <: Type](name: String, value: Expression[T] = UndefinedValue)(implicit ev: TypeTag[T]) { createVar[T, Int](name, value)(Left(ev): Either[TypeTag[T], universe.Type]) }
  // def createVar[T <: Type, X : ClassTag](pair: (String, Expression[T]))(implicit ev: TypeTag[T]) { createVar[T](pair._1, pair._2)(ev) }
  // def createVar[T <: Type, X : ClassTag](name: String, value: Expression[T])(ev: Either[TypeTag[T], universe.Type]) {
  //   ev match {
  //     case Left(tt)  => createThing[Tuple2[Value[Type], universe.Type]](name, (value.eval(this), tt.tpe), stack.asInstanceOf[ed.immutable.List[Map[String, Tuple2[Value[Type], universe.Type]]]], "Variavel")
  //     case Right(ut) => createThing[Tuple2[Value[Type], universe.Type]](name, (value.eval(this), ut    ), stack.asInstanceOf[ed.immutable.List[Map[String, Tuple2[Value[Type], universe.Type]]]], "Variavel")
  //   }
  createThing[Tuple2[Value[Type], universe.Type]](name, (value.eval(this), ev), stack.asInstanceOf[ed.immutable.List[Map[String, Tuple2[Value[Type], universe.Type]]]], "Variavel")
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

  def setVar[T <: Type](pair: (String, Expression[T]))(implicit ev: TypeTag[T]) { setVar[T](pair._1, pair._2)(ev) }
  def setVar[T <: Type](name: String, value: Expression[T])(implicit ev: TypeTag[T]) { setVar[T](name, value, ev.tpe) }
  // universe.Type overload!!!
  def setVar[T <: Type](name: String, value: Expression[T], ev: universe.Type) {
    if (stack.isEmpty)
      throw EmptyContextException("Contexto vazio")

    // checa no stack inteiro pra achar a variável
    val myIte = stack.getIterator
    while (myIte.hasNext) {
      if (myIte.value.hasKey(name)) {
        myIte.value.get(name) match {
          case None    => // nunca vai acontecer
          case Some(p) => {
          // se o tipo é o mesmo
            if (p._2 =:= ev) myIte.value.insert(name -> (value.eval(this), ev))
          // se o tipo não é o mesmo
            else throw IncompatibleTypeException("tentando interpretar uma variavel \""+p._1+"("+p._2+")\" como sendo \""+ev+"\"")
          }
        }
        return
      }
      else myIte.next()
    }

    if (myIte.value.hasKey(name))
      myIte.value.insert(name -> (value.eval(this), ev))
    // checou no stack inteiro e não achou.
    else
      throw InexistentThing("tentando mudar valor de variável não criada")
  }
  
  def clear() {
    while (!stack.isEmpty) removeLayer
  }
}

object Type2Either {
  implicit def type2Either[T](tt: TypeTag[T]): Either[TypeTag[T], universe.Type] = Left(tt)
}
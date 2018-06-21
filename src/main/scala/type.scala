package types

import value.{ValCreate, Value}

import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}
import scala.reflect.runtime.universe

abstract class Type

abstract class TypeUndef extends Type

case class TypeInt(val value: Int) extends Type {
  def apply(): Int = value
}

case class TypeBool(val value: Boolean) extends Type {
  def apply(): Boolean = value
}

object TypeInt {
  def getType(): universe.Type = typeOf[TypeInt]
  def apply(): universe.Type = getType()
}
object TypeBool {
  def getType(): universe.Type = typeOf[TypeBool]
  def apply(): universe.Type = getType()
}

object ImplicitTyping {
  implicit def typeInt2univType(a: TypeInt.type): universe.Type = a.getType()
  implicit def typeBool2univType(a: TypeBool.type): universe.Type = a.getType()
}

// conversão implicita de TypeInt pra TypeBool
object IntAsBool {
  implicit def int2Bool(a: TypeInt): TypeBool = a match {
    case TypeInt(0) => TypeBool(false)
    case _          => TypeBool(true)
  }
  implicit def intVal2BoolVal(a: Value[TypeInt]): Value[TypeBool] = Value(int2Bool(a.innerValue))
}
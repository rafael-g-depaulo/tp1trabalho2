package types

import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}
import scala.reflect.runtime.universe

import scala.language.implicitConversions

abstract class Type {
  // def getType(): universe.Type 
}

// object Type {
//   def getType(): universe.Type = typeOf[Type]
// }

class TypeUndef extends Type {
  // override def getType(): universe.Type = typeOf[TypeUndef]
}

case class TypeInt(val value: Int) extends TypeUndef {
  def apply(): Int = value
  // override def getType(): universe.Type = typeOf[TypeInt]
}

case class TypeBool(val value: Boolean) extends TypeUndef {
  def apply(): Boolean = value
  // override def getType(): universe.Type = typeOf[TypeInt]
}

object TypeInt {
  def getType(): universe.Type = typeOf[TypeInt]
}
object TypeBool {
  def getType(): universe.Type = typeOf[TypeBool]
}

// // não funcionando por algum motivo
// object implicitConversions {
//   implicit def int2TypeInt(value: Int): TypeInt = new TypeInt(value)
// }
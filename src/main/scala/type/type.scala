package types

abstract class Type
import scala.language.implicitConversions

class TypeUndef extends Type

case class TypeInt(val value: Int) extends TypeUndef {
  def apply(): Int = value
}

case class TypeBool(val value: Boolean) extends TypeUndef {
  def apply(): Boolean = value
}

// não funcionando por algum motivo
object implicitConversionss {
  implicit def int2TypeInt(value: Int): TypeInt = new TypeInt(value)
}
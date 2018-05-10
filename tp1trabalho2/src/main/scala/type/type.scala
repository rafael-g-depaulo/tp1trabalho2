package types

abstract class Type
import scala.language.implicitConversions

case class TypeInt(val value: Int) extends Type {
  def apply(): Int = value
}

case class TypeBool(val value: Boolean) extends Type {
  def apply(): Boolean = value
}

// não funcionando por algum motivo
object implicitConversionss {
  implicit def int2TypeInt(value: Int): TypeInt = new TypeInt(value)
}
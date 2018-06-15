package value

import ed.exceptions._
import types._
import context.Context
import exceptions._
import expression.Expression

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeTag, typeOf}

abstract sealed class Value[+T <: Type](ev: universe.Type) extends Expression[T](ev) {
  def innerValue: T
  def eval[T1 >: T <: Type](context: Context): Value[T1] = this
}

private case class ValCreate[+T <: Type](val value: T, implicit val ev: universe.Type) extends Value[T](ev) {
  def innerValue: T = value
  // override def getExprType[T1 >: T <: Type](implicit ev: TypeTag[T1]): universe.Type = value match {
  //   case _: TypeInt  => typeOf[TypeInt]
  //   case _: TypeBool => typeOf[TypeBool]
  //   case _           => throw ShoulNotHappenException("match em value.ValCreate nao foi exaustivo. novo tipo criado e mal implementado") 
  // }
}

object Value {
  def apply[T <: Type](): Value[T] = UndefinedValue.asInstanceOf[Value[T]]
  def apply[T <: Type](value: T): Value[T] = value match {
    case _: TypeInt  => ValCreate[TypeInt](value.asInstanceOf[TypeInt], typeOf[TypeInt]).asInstanceOf[Value[T]]
    case _: TypeBool => ValCreate[TypeBool](value.asInstanceOf[TypeBool], typeOf[TypeBool]).asInstanceOf[Value[T]]
    case _           => throw ShoulNotHappenException("match em value.Value nao foi exaustivo. novo tipo criado e mal implementado") 
  }
}

case object UndefinedValue extends Value[Nothing](typeOf[Nothing]) {
  override def innerValue: Nothing                                          = throw AccessingUndefinedException("tentando pegar valor de Undefined") 
  // override def eval[T1 >: Nothing <: Type](context: Context): Value[T1]     = throw AccessingUndefinedException("tentando avaliar(evaluate) Undefined") 
}
package expression

import types._
import ed.mutable._
import exceptions._
import value.Value
import context.Context

import scala.reflect.runtime.universe
import scala.reflect.runtime.universe.{TypeTag, typeOf}

abstract class Expression[+T <: Type] {
  def eval[T1 >: T <: Type](context: Context): Value[T1]
  def getExprType[T1 >: T <: Type](implicit ev: TypeTag[T1]): universe.Type
}
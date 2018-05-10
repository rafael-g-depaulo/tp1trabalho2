package ed
package mutable

protected case class Pair[A, B](val key: A, val value: B) {
  override def toString(): String = "Pair(" + key.toString() + ", " + value.toString() + ")" 
}
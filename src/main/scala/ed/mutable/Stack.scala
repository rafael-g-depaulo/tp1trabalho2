package ed.mutable

/**
  * Uma especificação do tipo pilha
  * usando a construção trait da linguagem
  * Scala.
  *
  * @author thaleslim
  */
trait Stack[T] {
  def push(value: T): Unit
  def pop(): Option[T]
  def top(): Option[T] 
  def size(): Int
}

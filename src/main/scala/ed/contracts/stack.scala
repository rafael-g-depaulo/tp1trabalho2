package ed
package contracts

/**
 * Classe abstrata que define o contrato de uma fila (Queue).
 *
 * @author Vinicius
 */

abstract class Stack[T] extends EstLin[T] {
  def peek: Option[T] = head
}

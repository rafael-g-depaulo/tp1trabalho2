package ed
package contracts

import scala.reflect.ClassTag

/**
 * Classe abstrata que define o contrato de uma fila (Queue).
 *
 * @author Rafael G. de Paulo
 */
abstract class Queue[T] extends EstLin[T] {

  /**
   * Adiciona um valor do tipo T a fila. Funciona de forma FIFO. Ou seja,
   *  um elemento inserido com push vai para a cauda(tail) da fila, e com
   *  cada pop() ele se aproxima da cabeça(head).
   *
   * @param value valor a ser adicionado a fila, de tipo T
   *
   * @return false se a implementação é estática, e a fila já estava cheia. Em qualquer outra situação, true
   */
  def push(value: T): Unit

  /**
   * retira um elemento da fila, e retorna seu valor. caso esteja vazia, não altera ela e retorna None
   */
  def pop(): Option[T]
  
  /**
   * retorna o valor do elemento "inicial", "primeiro" ou "cabeça" da fila
   */
  def head: Option[T]
  
  /**
   * retorna o valor do elemento "final", "último" ou "cauda" da fila
   */
  def tail: Option[T]
  
  /**
   * retorna a quantidade de elementos atualmente na fila
   */
  def size: Int

  def copy(implicit ev: ClassTag[T]): EstLin[T] = {
    val copy = instantiate[T](0)
    foreach {copy.push(_)}
    copy
  }
}
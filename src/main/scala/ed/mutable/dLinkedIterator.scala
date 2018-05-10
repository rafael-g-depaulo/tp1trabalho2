package ed
package mutable

import contracts._
import modifications._
import exceptions._

/**
 *  Classe que define e implementa o comportamento
 * de um iterator para uma Estrutura de Dados Linear
 * Duplamente Encadeada.
 *
 * @author Rafael G. de Paulo
 */
// T: O tipo de dado que a Estrutura Linear Guarda
// NodeImp: O tipo do Node que a Estrutura Linear usa
class DLinkedIterator[T, NodeImp <: DNode[T, NodeImp]](
  estLinRef: DLinked[T, NodeImp],
  startNode:   NodeImp
) extends LinkedIterator[T, NodeImp](estLinRef, startNode) {

  def hasPrev: Boolean = node.prev match {
    case Some(aNode) => true
    case None        => false
  }

  def prev() {
    node.prev match {
      case Some(prevNode) => node = prevNode
      case None           => throw new IteratorOutOfBounds("tentando dar prev() em um iterator no primeiro elemento")
    }
  }
  
}
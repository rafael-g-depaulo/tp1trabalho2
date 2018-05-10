package ed
package mutable

import scala.reflect.ClassTag

import modifications._
import contracts._
import exceptions._

/**
 * Implementação duplamente encadeada de de Queue(fila).
 *
 * @author Rafael G. de Paulo
 */ 
case class LQueue[T](values: T*) extends Queue[T]
with DLinked[T, LQueueNode[T]]
with Foreach[T]
with Reduce[T]
with Filter[T]
with Mappable[T]
{
  private var _size: Int = 0											// o tamanho da fila
  private var _head: Option[LQueueNode[T]] = None	// referência a cabeça da fila
  private var _tail: Option[LQueueNode[T]] = None	// referência a cauda da fila

  /** única parte do Construtor que faz alguma coisa **********************************************************************************************/
  values foreach { this.push(_) } // colocando todos os argumentos para a LQueue
  /** única parte do Construtor que faz alguma coisa **********************************************************************************************/

  def instantiate[A: ClassTag](inc: Int = 0): LQueue[A] = new LQueue[A]
  def size = _size																// retorna o tamanho da fila
  def isEmpty = size == 0
  def push(value: T) {                            // coloca um valor em um elemento, e adiciona ele à cauda da fila
    _tail match {
      case Some(node) => {                        // fila não-vazia
        node.next = Some(LQueueNode[T](value, _tail, None))
        _tail = node.next
      }
      case None => {                              // fila vazia
        _head = Some(LQueueNode[T](value))
        _tail = _head 
      }
    }
    _size += 1
  }
  def pop(): Option[T] = _head match {            // retira um elemento da fila, se possível, e retorna o seu valor interno
    case None       => None                       // fila vazia
    case Some(node) => {                          // fila não vazia
      _head = node.next
      _head match { case Some(anode) => anode.prev = None case None => }
      _size -= 1
      Some(node.value)
    }
  }
  def head: Option[T] = _head match {							// retorna o valor do elemento da cabeça, se existe
		case Some(v) => Some(v.value)
		case None => None
	}
  def tail: Option[T] = _tail match {							// retorna o valor do elemento da cauda, se existe
		case Some(v) => Some(v.value)
		case None => None
	}
  
  def next(node: LQueueNode[T])        = node.next  // retorna o próximo node
  def prev(node: LQueueNode[T])        = node.prev  // retorna o node anterior
  def firstNode: Option[LQueueNode[T]] = _head      // retorna a cabeça da fila (assim foreach itera nela da cabeça a cauda)
  def lastNode:  Option[LQueueNode[T]] = _tail      // retorna a cauda da fila

  override def getIterator(ind: Int): LQueueIterator[T] = {
    def getCorrectNode(i: Int, node: LQueueNode[T], fwd: Boolean): LQueueNode[T] = {
      if (i < 0)
        getCorrectNode(size + (i % size), node, fwd)
      else
        (i % size compare 0) match {
          case -1 => throw new PoorlyImplemented("LQueue.getIterator() poorly implemented.")     // não deve nunca acontecer
          case  0 => node
          case  1 => node.next match {
            case Some(nextNode) => getCorrectNode(i - 1, nextNode, fwd)
            case None => throw new PoorlyImplemented("LQueue.getIterator() poorly implemented.") // não deve nunca acontecer
          }
        }}

    _head match {
      case None       => throw new EmptyEDIterator("tentando criar Iterator para um LQueue vazio")
      case Some(node) => LQueueIterator[T](this, getCorrectNode(ind, node, true))
    }
  }

  override def copy(implicit ev: ClassTag[T]): EstLin[T] = {
    val queue = instantiate[T](0)
    foreach { queue.push(_) }
    queue
  }
}

case class LQueueNode[T](
  val _value: T,                              		// o valor do Node
  var   prev: Option[LQueueNode[T]] = None,				// referência ao Node anterior (mais próximo da cabeça)
  var   next: Option[LQueueNode[T]] = None				// referência ao próximo Node  (mais próximo da cauda)
) extends DNode[T, LQueueNode[T]]

case class LQueueIterator[T](
  queue: LQueue[T],
  startNode:  LQueueNode[T]
) extends DLinkedIterator[T, LQueueNode[T]](queue, startNode)
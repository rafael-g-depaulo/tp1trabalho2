// // package mylib
// // package mutable

// import mylib.modifications._
// import mylib.contracts._
// import mylib.exceptions._
// import scala.reflect.ClassTag
//  /**
//   * Implementação encadeada de Stack
//   *
//   * @author Lucas Vinicius
//   */

//  /**
// 	* Definição de um Node de Stack, contendo o valor e ponteiro pro próximo Node.
// 	*/
// case class LStackNode[T](
// 	val _value: T,
// 	var next:   Option[LStackNode[T]] = None
// ) extends Node[T, LStackNode[T]]

// case class LStackIterator[T](
//   stack: LStack[T],
//   startNode:  LStackNode[T]
// ) extends LinkedIterator[T, LStackNode[T]](stack, startNode)
 
// case class LStack[T](values: T*) extends Stack[T]
// with Linked[T, LStackNode[T]]
// with Filter[T]
// with Foreach[T]
// with Map[T]
// with Reduce[T]
// {
// 	private var _head: Option[LStackNode[T]] = None
// 	private var _tail: Option[LStackNode[T]] = None
// 	private var _size: Int                   = 0
// 	values foreach {this.push(_)}	
	
// 	def instantiate[A: ClassTag](inc: Int): LStack[A] = new LStack[A]
//  /**
// 	*@return inteiro _size contendo o tamanho da Stack
// 	*/
// 	def size = _size
//  /**
// 	*@return true se a Stack está vazia
// 	*/
// 	def isEmpty = size == 0
//  /**
// 	*@return Some(valor) correspondente ao topo da Stack ou None se a Stack tiver vazia
// 	*/
// 	def head: Option[T] = _head match {
// 		case None       => None
// 		case Some(node) => Some(node.value)
// 	}
//  /**
// 	*@return Some(valor) correspondente ao primeiro item da Stack ou None se a Stack tiver vazia
// 	*/
// 	def tail: Option[T] = _tail match {
// 		case None       => None
// 		case Some(node) => Some(node.value)
// 	}
//  /**
// 	*@return Some(valor) correspondente ao topo da Stack ou None se a Stack tiver vazia, retirando o valor e decrementando o tamanho da Stack
// 	*/
// 	def pop(): Option[T] = _head match {
// 		case None       => None
// 		case Some(node) => {
// 			_head = node.next
// 			_size -= 1
// 			Some(node.value)
// 		}
// 	}
//  /**
// 	*Põe um valor na Stack e incrementa seu tamanho
// 	*/
// 	def push(value: T) {
// 		_head match{
// 			case Some(node) => {
// 				_head = Some(LStackNode[T](value, Some(node)))
// 				_size += 1
// 			}
// 			case None       => {
// 				_head = Some(LStackNode[T](value, None))
// 				_tail = _head
// 				_size += 1
// 			}
// 		}  
// 	}
//  /**
// 	*@return Some(valor) igual a _head, se houver
// 	*/
// 	def firstNode: Option[LStackNode[T]] = _head
//  /**
// 	*@return Some(valor) igual a _tail, se houver
// 	*/
// 	def lastNode:  Option[LStackNode[T]] = _tail
//  /**
// 	*@return Some(LStackNode[T]) apontando para o próximo Node, se houver
// 	*/
// 	def next(node: LStackNode[T])        = node.next

// 	  override def getIterator(ind: Int): LStackIterator[T] = {
//     def getCorrectNode(i: Int, node: LStackNode[T], fwd: Boolean): LStackNode[T] = {
//       if (i < 0)
//         getCorrectNode(size + (i % size), node, fwd)
//       else
//         (i % size compare 0) match {
//           case -1 => throw new PoorlyImplemented("LQueue.getIterator() poorly implemented.")     // não deve nunca acontecer
//           case  0 => node
//           case  1 => node.next match {
//             case Some(nextNode) => getCorrectNode(i - 1, nextNode, fwd)
//             case None => throw new PoorlyImplemented("LQueue.getIterator() poorly implemented.") // não deve nunca acontecer
//           }
//         }}

//     _head match {
//       case None       => throw new EmptyEDIterator("tentando criar Iterator para um LQueue vazio")
//       case Some(node) => LStackIterator[T](this, getCorrectNode(ind, node, true))
//     }
//   }
	
// }

// // 	override def map[B: ClassTag](foo: (T) => B): EstLin[B] = {
// //     val stackInv:    Stack[B] = instantiate[B]()
// // 		val stackMapped: Stack[B] = instantiate[B]()
// //     foreach { (value: T) => stackInv push( foo(value) ) }
// //     stackInv foreach {stackMapped.push(_) }
// // 		stackMapped
// // 	}
	
// // }
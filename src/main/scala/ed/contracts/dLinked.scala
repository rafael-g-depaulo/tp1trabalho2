package ed
package modifications

import contracts._

/**
 *  Trait que define o comportamento de
 * encadeamento duplo que uma Estrutura
 * de Dados linear pode ter.
 *
 * @author Rafael G. de Paulo
 */
trait DLinked[T, ImpNode <: DNode[T, ImpNode]] extends Linked[T, ImpNode] {
  /**
   * Pega o último nó da estrutura. a própria implementação da Estrutura
   *  define qual é o último nó
   *
   * @return Some(ImpNode) se a EstLin[T] não estiver vazia
   * @return None          se a EstLin[T] estiver vazia
   */
  protected def lastNode: Option[ImpNode]

  /**
   * Pega o elemento anterior da estrutura
   *
   * @param node o nó do qual o anterior deve ser retornado
   *
   * @return Some(ImpNode) se o node não é o último da Estrutura Linear
   * @return None          se o node é o último da Estrutura Linear
   *   
   */
  protected def prev(node: ImpNode): Option[ImpNode]  // segua a estrutura na ordem que ela preferir
}
package ed
package contracts

/**
 *  Classe abstrata que define o contrato de um
 * Node que contém um valor de T, uma referencia
 * ao próximo Node e ao Node anterior
 *
 * @author Rafael G. de Paulo
 */
abstract class DNode[T, NodeType <: DNode[T, NodeType]] extends Node[T, NodeType] { this: NodeType =>
  /**
   * Referência ao Node anterior
   */
  var prev: Option[NodeType]
}

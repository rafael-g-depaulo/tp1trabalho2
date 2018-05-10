package ed
package contracts

/**
 *  Trait que define o comportamento estático
 * que uma Estrutura de Dados Linear pode ter.
 *
 * OBS: se uma EslLin[T] não mixar "static", ela DEVE mixar "linked", e vice-versa
 *  @see mylib/contracts/linked.scala
 *
 * OBS2: apesar do DEVE ali em cima, até onde o meu conhecimento de Scala vai, não
 *  tem como forçar o cliente que for implementar uma EstLin de mixar um dos dois
 *
 * @author Rafael G. de Paulo
 */
trait Static[T] extends EstLin[T] {
  def maxSize: Int  // retorna o tamanho máximo da Estrutura Linear
}
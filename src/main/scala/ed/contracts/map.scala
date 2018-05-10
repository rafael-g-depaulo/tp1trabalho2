package ed
package contracts

import scala.reflect.ClassTag

/**
 *   Classe abstrata que define o contrato
 * de um Mapa.
 *
 *   Um mapa é parametrizado em A e B. A é
 * o tipo da Chave, e B o do Valor. Pares
 * A -> B podem ser inseridos no mapa, e 
 * depois recuperados, inserindo a chave
 * A no mapa, que retorna o valor B.
 * 
 *   Dependendo da implementação do Mapa,
 * podem ocorrer colisões, em que uma chave
 * é ligada a mais de um valor. A implementação
 * então decide como lidar com as colisões.
 * 
 * @author Rafael G. de Paulo
 */
abstract class Map[A, B] {
  
  def insert(pair: (A, B)*): Unit
  def get(key: A): Option[B]
  def size: Int
  def hasKey(key: A): Boolean
  def hasObject(obj: B): Boolean
}
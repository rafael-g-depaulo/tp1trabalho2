package ed
package contracts

import scala.reflect.ClassTag

/**
 *  Classe abstrata que define o contrato
 * de uma Estrutura de Dados linear mutável.
 *
 * OBS: se uma EslLin[T] não mixar "static", ela DEVE mixar "linked", e vice-versa
 *  @see mylib/contracts/linked.scala
 *  @see mylib/contracts/static.scala
 *
 * @author Rafael G. de Paulo
 */
abstract class EstLin[T] {
  /**
   * Retorna uma instancia dessa implementação de uma estrutura linear que guarda valores do tipo A.
   * 
   * @param inc o quanto que a estrutura deve aumentar de tamanho máximo. só faz alguma coisa se a estrutura for não-encadeada.
   * Ex: Em uma instancia de fila estática com tamanho máximo 10, de nome myFila, "myFila.instantiate[A](5)" retorna um nova
   *   instancia da classe de myFila, com tamanho máximo de 15. Um exemplo de uso prático é no trait append, em que um método
   *   usa duas EstLin[T], e retorna uma nova, com tamanho máximo igual a soma do tamanho máximo das duas originais
   *
   */
  protected def instantiate[A: ClassTag](inc: Int = 0): EstLin[A] 

  /**
   * Retorna o valor do elemento "inicial", "primeiro" ou "cabeça" da estrutura linear, se existir
   */
  def head: Option[T]
    
  /**
   * Retorna o valor do elemento "final", "último" ou "cauda" da estrutura linear. se existir
   */
  def tail: Option[T]

  /**
   * Retorna a quantidade de elementos atualmente na estrutura linear
   */
  def size: Int
  
  /**
   * Insere um valor na estrutura linear.
   */
  def push(value: T): Unit
  
  /**
   * Remove um valor da estrutura linear
   */
  def pop(): Option[T]
  
  /**
   * Executa a função inserida em cada elemento da estrutura linear 
   */
  def foreach(foo: (T) => Unit): Unit

  /**
   * Retorna uma cópia dessa EstLin
   */
  def copy(implicit ev: ClassTag[T]): EstLin[T]

  /**
   * Pega um Iterator da Estrutura Linear
   *
   * @param ind indice base-0 de em qual valor começar. Se ind < 0,
   *   o valor em que o Iterator começa é contado de trás pra frente.
   *   Ex: getIterator(-1) retorna um iterator que aponta pro último
   *   dado, e getIterator(-2) retorna um iterator que aponta pro
   *   penúltimo dado, etc.
   */
  def getIterator(ind: Int = 0): Iterator[T]

  protected def getIteratorIndex(ind: Int = 0): Int = if (ind < 0) ind % size + size else ind % size

  /**
   * Checa se a Estrutura Linear está vazia ou não
   */
  def isEmpty: Boolean
}
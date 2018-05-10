package ed
package modifications

import contracts.{EstLin, Node}

/**
 *  Trait que implementa foreach() automaticamente em
 * uma Estrutura de Dados Linear, usando seu Iterator
 * 
 *  Executa a função inserida em cada elemento da estrutura linear
 *
 * @author Rafael G. de Paulo
 */
// T: o tipo de dado guardado pela Estrutura de Dados Linear
trait Foreach[T] extends EstLin[T] {
  // executa a função inserida em cada elemento da estrutura linear
  override def foreach(foo: (T) => Unit) {

    def iterate(iter: Iterator[T]) {
      foo(iter.value)                             // execute foo
      if (iter.hasNext) {
        iter.next
        iterate(iter)
      }
    }

    // se a EstLin não está vazia, itera nela
    if (!isEmpty)
      iterate(getIterator())
  }
}

package ed.mutable

/**
  * Uma implementacao do tipo pilha usando
  * alocacao sequencial (um array de elementos).
  *
  * @author: thaleslim / rbonifacio
  */
class StackImpl[T](private val elements: ed.mutable.List[T]) extends Stack[T] {

  private var _size = 0

  def push(value: T): Unit = {
    elements.insert(_size, value)
    _size += 1
  }

  def pop(): Option[T] = {
    val res = elements.elementAt(_size-1)
    res match {
      case Some(v) => {
        elements.remove(_size-1)
        _size -= 1
      }
      case None => {}
    }
    return res
  }

  def top(): Option[T] = elements.elementAt(_size-1)

  def size() : Int = _size
}

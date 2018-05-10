package ed.mutable

import ed.exceptions._

/**
  * Uma implementacao do tipo lista usando
  * alocacao sequencial (um array de elementos).
  *
  * @author: rbonifacio
  */
class ArrayList[T: Manifest](private val max: Int = 10) extends List[T] {

  private var _size = 0;
  private var elements = Array.ofDim[T](max)

  def insert(pos: Int, value: T): Unit = {
    if(pos >= 0 && pos <= _size && pos < max) {
      if(pos == _size) {
        elements(pos) = value
      }
      else {
        for(index <- (_size-1) to pos by -1){
          elements(index + 1) = elements(index)
        }
        elements(pos) = value
      }
      _size += 1
    }
    else throw InvalidArgument("the first argument must be between 0 and size")
  }

  def elementAt(pos: Int): Option[T] = {
    if(pos >= 0 && pos < _size) {
      return Some(elements(pos))
    }
    return None
  }

  def find(value: T): Option[Int] = {
    for(idx <- 0 until _size) {
      if(value == elements(idx)) {
        return Some(idx)
      }
    }
    return None
  }

  def remove(pos: Int): Unit = {
    if(pos >= 0 && pos < _size) {
      if(pos != _size-1){
        for(index <- pos until (_size-1)){
          elements(index) = elements(index+1)
        }
      }
      _size -= 1
    }
    else throw InvalidArgument("the first argument must be between 0 and size")
  }

  def size(): Int = _size

  def addAll[B <: T](values: List[B]): Unit = {
    if(values.size() + _size > max) {
      throw new InvalidArgument("overflow!!!")
    }

    for(i <- 0 until values.size()) {
      values.elementAt(i) match {
        case Some(v) => insert(_size, v)
        case _       => throw new InvalidArgument("ooops..... unexpected")
      }
    }
  }

}

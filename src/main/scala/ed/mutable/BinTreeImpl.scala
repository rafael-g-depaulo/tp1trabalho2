package ed.mutable

class NodeTree[T <: Comparable[T]](val content: T, var lhs: NodeTree[T] = null, var rhs: NodeTree[T] = null) {

  def insert(v: T) {
    if(v.compareTo(content) <= 0) {
      if(lhs == null) lhs = new NodeTree(v)
      else lhs.insert(v)
    }
    else {
      if(rhs == null) rhs = new NodeTree(v)
      else rhs.insert(v)
    }
  }


  def exist(v: T) : Boolean = 
    if(v.compareTo(content) == 0) true
    else if ((v.compareTo(content) <= 0) && lhs != null) lhs.exist(v)
    else if (rhs != null) rhs.exist(v)
    else false 
}


class BinTreeImpl[T <: Comparable[T]] extends BinTree[T] {
  var root: NodeTree[T] = null

  override def insert(v: T) {
    if(root == null) root = new NodeTree(v)
    else root.insert(v) 
  }

  override def exist(v: T): Boolean  = if(root == null) false else root.exist(v) 

}

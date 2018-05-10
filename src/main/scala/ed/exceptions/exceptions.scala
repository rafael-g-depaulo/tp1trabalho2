package ed
package exceptions

final case class EmptyEDIterator     (msg: String) extends Exception(msg)
final case class IndexOutOfBounds    (msg: String) extends Exception(msg)
final case class NegativeIndex       (msg: String) extends Exception(msg)
final case class IteratorOutOfBounds (msg: String) extends Exception(msg)
final case class PoorlyImplemented   (msg: String) extends Exception(msg)
final case class NotSuported         (msg: String) extends Exception(msg)


final case class InvalidArgument(private val message: String = "",
  private val cause: Throwable = None.orNull) extends Exception(message, cause)


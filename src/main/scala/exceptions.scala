package exceptions

final case class InvalidVariableName(msg: String) extends Exception(msg)
final case class InexistentVariable(msg: String) extends Exception(msg)
final case class StackUnderflowException(msg: String) extends Exception(msg)
final case class EmptyContextException(msg: String) extends Exception(msg)
final case class AccessingUndefinedException(msg: String) extends Exception(msg)
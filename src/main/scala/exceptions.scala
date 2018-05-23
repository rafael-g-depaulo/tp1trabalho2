package exceptions

final case class InvalidName                 (msg: String) extends Exception(msg)
final case class InexistentThing             (msg: String) extends Exception(msg)
final case class StackUnderflowException     (msg: String) extends Exception(msg)
final case class EmptyContextException       (msg: String) extends Exception(msg)
final case class AccessingUndefinedException (msg: String) extends Exception(msg)
final case class NegativeDenomException      (msg: String) extends Exception(msg)
final case class ExecutingReturnException    (msg: String) extends Exception(msg)
final case class WrongParameterName          (msg: String) extends Exception(msg)
final case class IncompleArgumentList        (msg: String) extends Exception(msg)
final case class InvalidUseOfCommand         (msg: String) extends Exception(msg)
final case class FunctionWithoutReturn       (msg: String) extends Exception(msg)
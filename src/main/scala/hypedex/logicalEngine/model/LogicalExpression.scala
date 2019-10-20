package hypedex.logicalEngine.model

abstract class LogicalExpression(val value: Double, val isInclusive: Boolean) {
  def isWithinRange(otherValue: Double): Boolean
}

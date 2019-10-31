package hypedex.logicalEngine.model

/**
  *
  * @param value
  * @param isInclusive
  * @param direction should be -1, 0 or 1.
  */
abstract class LogicalExpression(val value: Double, val isInclusive: Boolean, val direction: Int) {
  assert(direction == 0 || direction == -1 || direction == 1)

  def isWithinRange(otherValue: Double): Boolean
}

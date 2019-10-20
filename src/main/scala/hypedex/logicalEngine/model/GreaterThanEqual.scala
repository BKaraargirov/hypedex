package hypedex.logicalEngine.model

case class GreaterThanEqual(override val value: Double) extends LogicalExpression(value, true) {
  override def isWithinRange(otherValue: Double): Boolean = this.value <= otherValue
}

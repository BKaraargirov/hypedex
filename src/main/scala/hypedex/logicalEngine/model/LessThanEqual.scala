package hypedex.logicalEngine.model

case class LessThanEqual(override val value: Double) extends LogicalExpression(value, true, -1) {
  override def isWithinRange(otherValue: Double): Boolean = this.value >= otherValue
}

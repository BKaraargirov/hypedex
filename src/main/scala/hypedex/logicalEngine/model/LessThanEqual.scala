package hypedex.logicalEngine.model

case class LessThanEqual(override val value: Double) extends LogicalExpression(value, true) {
  override def isWithinRange(otherValue: Double): Boolean = this.value >= otherValue
}

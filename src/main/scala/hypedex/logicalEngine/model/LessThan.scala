package hypedex.logicalEngine.model

case class LessThan(override val value: Double) extends LogicalExpression (value, false) {
  override def isWithinRange(otherValue: Double): Boolean = this.value > otherValue
}

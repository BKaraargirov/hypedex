package hypedex.logicalEngine.model

case class Equals(override val value: Double) extends LogicalExpression(value, true) {
  override def isWithinRange(otherValue: Double): Boolean = this.value == otherValue
}

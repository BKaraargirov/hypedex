package hypedex.queryAnalyzer.models

case class GreaterThanEqual(override val value: Double) extends LogicalExpression(value, true, 1) {
  override def isWithinRange(otherValue: Double): Boolean = this.value <= otherValue
}

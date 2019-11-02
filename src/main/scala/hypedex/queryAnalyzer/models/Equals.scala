package hypedex.queryAnalyzer.models

case class Equals(override val value: Double) extends LogicalExpression(value, true, 0) {
  override def isWithinRange(otherValue: Double): Boolean = this.value == otherValue
}

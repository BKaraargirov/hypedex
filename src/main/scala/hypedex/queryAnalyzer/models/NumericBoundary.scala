package hypedex.queryAnalyzer.models

class NumericBoundary(
  val lowerBound: Double,
  val upperBound: Double
) {
  def hasIntersection(expression: LogicalExpression): Boolean = {
    expression match {
      case _ if expression.isDirectionPositive && expression.isInclusive =>
        upperBound >= expression.value
      case _ if expression.isDirectionPositive && expression.isInclusive == false =>
        upperBound > expression.value
      case _ if expression.isDirectionNegative && expression.isInclusive =>
        lowerBound <= expression.value
      case _ if expression.isDirectionNegative && expression.isInclusive == false =>
        lowerBound < expression.value
      case _ if expression.isDirectionZero => lowerBound <= expression.value && expression.value <= upperBound
    }
  }
}
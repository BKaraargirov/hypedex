package hypedex.queryAnalyzer.models

import scala.collection.immutable.SortedSet

case class NumericBoundary(
  lowerBound: Double,
  upperBound: Double
) extends Ordered[NumericBoundary] {

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

  /**
    * Updates the boundary with a ne constraints.
    * @param expression
    * @return
    */
  def update(expression: LogicalExpression): NumericBoundary = {
    expression match {
      case LessThan(lt) if lt <= this.upperBound => NumericBoundary(this.lowerBound, lt - 1)
      case LessThanEqual(lte) if lte < this.upperBound => NumericBoundary(this.lowerBound, lte)
      case _ if expression.isDirectionNegative && expression.value >= this.upperBound => this

      case GreaterThan(gt) if gt >= this.lowerBound => NumericBoundary(gt + 1, this.upperBound)
      case GreaterThanEqual(gte) if gte > this.lowerBound => NumericBoundary(gte, this.upperBound)
      case _ if expression.isDirectionPositive && expression.value <= this.lowerBound => this

      case Equals(eq) if this.lowerBound <= eq || eq <= this.upperBound => NumericBoundary(eq, eq)

      case _ => NEBoundary()
    }
  }

  // return 0 if the same, negative if this < that, positive if this > that
  def compare (that: NumericBoundary): Int = {
    if (this.lowerBound == that.lowerBound && this.upperBound == that.upperBound)
      0
    // this contains that, which should not be possible but we act if they are equivalent
    else if (this.lowerBound < that.lowerBound && this.upperBound > that.upperBound)
      0
    else if (this.upperBound < that.lowerBound)
      -1
    else if (this.lowerBound < that.lowerBound && this.upperBound < that.upperBound)
      -1
    else // if (this.lowerBound > that.upperBound || (this.loweBound > that.loweBound && this.upperBound > that.upperBound))
      1
  }
}

object NumericBoundary {
  def apply(expression: LogicalExpression) : NumericBoundary = {
    expression match {
      case LessThan(lt) => NumericBoundary(Double.NegativeInfinity, lt - 1)
      case LessThanEqual(lte) => NumericBoundary(Double.NegativeInfinity, lte)
      case Equals(eq) => NumericBoundary(eq, eq)
      case GreaterThan(gt) => NumericBoundary(gt + 1, Double.PositiveInfinity)
      case GreaterThanEqual(gte) => NumericBoundary(gte, Double.PositiveInfinity)
    }
  }
}
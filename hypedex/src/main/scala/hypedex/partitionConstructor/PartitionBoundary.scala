package hypedex.partitionConstructor

import hypedex.queryAnalyzer.models._

@SerialVersionUID(-3638122912543770513L)
case class PartitionBoundary (
  dimensionName: String,
  lowerBound: PartitionLowerBound = PartitionLowerBound(Double.NegativeInfinity, false),
  upperBound: PartitionUpperBound = PartitionUpperBound(Double.PositiveInfinity, false)
) {
  // TODO: Test
  def updateBoundary(newBoundary: LogicalExpression): PartitionBoundary = {
    newBoundary match {
      case LessThan(value) =>
        if(value > this.lowerBound.value)
          PartitionBoundary(this.dimensionName, this.lowerBound, PartitionUpperBound(value, false))
        else
          this
      case GreaterThanEqual(value) =>
        if(value >= this.lowerBound.value)
          PartitionBoundary(this.dimensionName, PartitionLowerBound(value, true), this.upperBound)
        else
          this
      case _ => throw new UnsupportedOperationException(s"Logical expression ${newBoundary} not supported")
    }
  }

  def doesIntersectionExists(expression: LogicalExpression): Boolean = {
    def check(value: Double): Boolean =  this.lowerBound.value < value && value <= this.upperBound.value

    expression match {
      case LessThan(value) => check(value - 1)
      case LessThanEqual(value) => check(value)
      case Equals(value) => check(value)
      case GreaterThan(value) => check(value + 1)
      case GreaterThanEqual(value) => check(value)
    }
  }
}

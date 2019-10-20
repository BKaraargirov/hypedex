package hypedex.logicalEngine.model

//Rename
case class AxisSlice(
  lowerBound: LowerBound,
  upperBound: UpperBound
) extends Ordered[AxisSlice]  {
  def isBetween(logicalExpression: LogicalExpression, inclusive: Boolean): Boolean = inclusive match {
    case true => lowerBound.value <= logicalExpression.value && logicalExpression.value <= upperBound.value
    case false => lowerBound.value < logicalExpression.value && logicalExpression.value < upperBound.value
  }

  override def compare (that: AxisSlice): Int =
    this.upperBound.value compareTo that.lowerBound.value
}


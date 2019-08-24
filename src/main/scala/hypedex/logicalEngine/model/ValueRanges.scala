package hypedex.logicalEngine.model


case class ValueRanges(
  ranges: List[AxisBound] = List()
) {
  def insertNewBound(expression: LogicalExpression): ValueRanges = ???
}

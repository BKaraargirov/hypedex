package hypedex.logicalEngine.model

import scala.collection.immutable.SortedSet


case class ValueRanges(
  ranges: SortedSet[AxisBound] = SortedSet[AxisBound]()
) {
  def insertNewBound(expression: LogicalExpression): ValueRanges = ???
}

package hypedex.logicalEngine.model

import scala.collection.immutable.SortedSet


case class ValueRanges(
  private var ranges: SortedSet[AxisSlice] = SortedSet[AxisSlice](
    AxisSlice(LowerBound(Double.NegativeInfinity, false), UpperBound(Double.PositiveInfinity, false))
  )
) {


  def insertNewBound(expression: LogicalExpression): ValueRanges = {
    def findInsertionSlot(elementsLeft: SortedSet[AxisSlice]): SortedSet[AxisSlice] = {
      if(elementsLeft.isEmpty)
        return ranges

      val currentSlice = elementsLeft.head

      if((expression.value == currentSlice.lowerBound.value &&
        currentSlice.lowerBound.inclusive == true) || (
        expression.value == currentSlice.upperBound.value &&
          currentSlice.upperBound.inclusive == true
        )) {
        // all is good
      }

      if(expression.value == currentSlice.lowerBound.value &&
        currentSlice.lowerBound.inclusive == false) {
        //modify current
      }

      if(expression.value == currentSlice.upperBound.value &&
        currentSlice.upperBound.inclusive == false) {
        //modify current
      }





      if(expression isLessThan elementsLeft.head)


      if(expression isBetween elementsLeft.head) {

      }


    }

    ???
  }

  def shouldInsert(slice: AxisSlice, expression: LogicalExpression): Unit = {
    expression match {
      Equal
    }
  }

  /**
    * Holds the information of where to insert the element
    * @param index at which to slice or insert after
    * @param slice if true, we will slice the existing range with the new value, else we will insert after the index
    */
  private case class InsertOperation(
    index: Int,
    slice: Boolean
  )

  def canEqual(other: Any): Boolean = other.isInstanceOf[ValueRanges]

  override def equals(other: Any): Boolean = other match {
    case that: ValueRanges =>
      (that canEqual this) &&
        // We override equals because of this line: the default comparison of sorted set does not check the elements
        ranges.sameElements(that.ranges)
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(ranges)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object ValueRanges {
  def apply (slice: AxisSlice): ValueRanges =
    new ValueRanges ( SortedSet(slice) )
}

package hypedex.logicalEngine

import hypedex.logicalEngine.model.{AxisBound, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LowerBound, UpperBound, ValueRanges}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.SortedSet

class ValueRangesTests extends FlatSpec with Matchers {
  "Adding a new greated than expression to an empty range" should
    "create a range of Lowerbound not incluse to plus infinity" in {
    val greaterThan = GreaterThan(5)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(SortedSet(AxisBound(LowerBound(5, false), UpperBound(Double.PositiveInfinity, false))))

    val actualResult = valueRange.insertNewBound(greaterThan)

    expectedResult should equal(actualResult)
  }

  "Adding a new greated than and equal expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = GreaterThanEqual(0)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(SortedSet(AxisBound(LowerBound(0, true), UpperBound(Double.PositiveInfinity, false))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)
  }

  "Adding a new lower than expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = LessThan(10)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(SortedSet(AxisBound(LowerBound(Double.NegativeInfinity, false), UpperBound(10, false))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)

    expectedResult should equal(actualResult)
  }

  "Adding a new lower than equal expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = LessThanEqual(-10)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(SortedSet(AxisBound(LowerBound(Double.NegativeInfinity, false), UpperBound(-10, true))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)

    expectedResult should equal(actualResult)
  }

  "Adding a new equal expression to an empty range" should
    "create a range of Lowerbound incluse to same upper bound inclusive " in {
    val greaterThanEqual = Equals(3)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(SortedSet(AxisBound(LowerBound(3, true), UpperBound(3, true))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)

    expectedResult should equal(actualResult)
  }

  "Adding a new greater than to a non empty range with overlapping range" should
    "split on of the ranges" in {
    val greaterThan = GreaterThan(34)
    val valueRange = ValueRanges(
      SortedSet(
        AxisBound(LowerBound(-15, true), UpperBound(-7, true)),
        AxisBound(LowerBound(3, true), UpperBound(50, true))
      )
    )
    val expectedResult = ValueRanges(
      SortedSet(
        AxisBound(LowerBound(-15, true), UpperBound(-7, true)),
        AxisBound(LowerBound(34, false), UpperBound(50, true))
      )
    )

    val actualResult = valueRange.insertNewBound(greaterThan)

    expectedResult should equal(actualResult)
  }
}

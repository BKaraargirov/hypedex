package hypedex.logicalEngine

import hypedex.logicalEngine.model.{AxisBound, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LowerBound, UpperBound, ValueRanges}
import org.scalatest.{FlatSpec, Matchers}

class ValueRangesTests extends FlatSpec with Matchers {
  "Adding a new greated than expression to an empty range" should
    "create a range of Lowerbound not incluse to plus infinity" in {
    val greaterThan = GreaterThan(5)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(List(AxisBound(LowerBound(5, false), UpperBound(Double.PositiveInfinity, false))))

    val actualResult = valueRange.insertNewBound(greaterThan)
  }

  "Adding a new greated than and equal expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = GreaterThanEqual(0)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(List(AxisBound(LowerBound(0, true), UpperBound(Double.PositiveInfinity, false))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)
  }

  "Adding a new lower than expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = LessThan(10)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(List(AxisBound(LowerBound(Double.NegativeInfinity, false), UpperBound(10, false))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)
  }

  "Adding a new lower than equal expression to an empty range" should
    "create a range of Lowerbound incluse to plus infinity" in {
    val greaterThanEqual = LessThanEqual(-10)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(List(AxisBound(LowerBound(Double.NegativeInfinity, false), UpperBound(-10, true))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)
  }

  "Adding a new equal expression to an empty range" should
    "create a range of Lowerbound incluse to same upper bound inclusive " in {
    val greaterThanEqual = Equals(3)
    val valueRange = ValueRanges()
    val expectedResult = ValueRanges(List(AxisBound(LowerBound(3, true), UpperBound(3, true))))

    val actualResult = valueRange.insertNewBound(greaterThanEqual)
  }
}

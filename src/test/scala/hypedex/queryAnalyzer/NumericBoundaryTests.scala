package hypedex.queryAnalyzer

import hypedex.queryAnalyzer.models.{Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, NumericBoundary}
import org.scalatest.{FlatSpec, Matchers}

class NumericBoundaryTests extends FlatSpec with Matchers {
  "Inf,5" should "not intersect with  GreaterThan 5" in {
    val boundary = NumericBoundary(lowerBound = Double.NegativeInfinity, upperBound = 5)
    val exp = GreaterThan(5)
    val expectedResult = false

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "Inf,5" should "intersect with  GreaterThanEqual 5" in {
    val boundary = NumericBoundary(lowerBound = Double.NegativeInfinity, upperBound = 5)
    val exp = GreaterThanEqual(5)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "Inf,5" should "intersect with  GreaterThan -50" in {
    val boundary = NumericBoundary(lowerBound = Double.NegativeInfinity, upperBound = 5)
    val exp = GreaterThan(-50)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "Inf, 5" should "intersect with LessThan 10" in {
    val boundary = NumericBoundary(lowerBound = Double.NegativeInfinity, upperBound = 5)
    val exp = LessThan(10)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "intersect LessThanEqual 4" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = LessThanEqual(4)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "not intersect LessThan 4" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = LessThan(4)
    val expectedResult = false

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "intersect LessThan 6" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = LessThan(6)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "intersect equal 5" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = Equals(5)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "not intersect equal 16" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = Equals(16)
    val expectedResult = false

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "intersect NotEqual 5" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = NotEqual(5)
    val expectedResult = true

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }

  "4,10" should "not intersect NotEqual 16" in {
    val boundary = NumericBoundary(lowerBound = 4, upperBound = 10)
    val exp = NotEqual(16)
    val expectedResult = false

    val result = boundary.hasIntersection(exp)

    result shouldBe expectedResult
  }
}

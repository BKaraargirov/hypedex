package hypedex.queryAnalyzer

import hypedex.queryAnalyzer.models.{AndNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, NotNode, OrNode}
import org.scalatest.{FlatSpec, Matchers}

class LogicalEngineTests extends FlatSpec with Matchers {
  val unitUnderTest = LogicalEngine

  "Not And with Greater than" should "become Or with LessThanEquals" in {
    val original = NotNode(AndNode(List(GreaterThan(6))))
    val target = OrNode(List(LessThanEqual(6)))

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Not Or with Greater than" should "become And with LessThanEquals" in {
    val original = NotNode(OrNode(List(GreaterThan(6))))
    val target = AndNode(List(LessThanEqual(6)))

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Double negation" should "negate itself" in {
    val original = NotNode(AndNode(List(NotNode(GreaterThan(5)), NotNode(LessThan(-5)))))
    val target =OrNode(List(GreaterThan(5), LessThan(-5)))

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Negating GreaterThan" should "result in LessThanEqual" in {
    val original = NotNode(GreaterThan(5))
    val target = LessThanEqual(5)

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Negating GreaterThanEqual" should "result in LessThan" in {
    val original = NotNode(GreaterThanEqual(5))
    val target = LessThan(5)

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Negating LessThan" should "result in GreaterThanEqual" in {
    val original = NotNode(LessThan(-10))
    val target = GreaterThanEqual(-10)

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Negating LessThanEquals" should "result in GreaterThan" in {
    val original = NotNode(LessThanEqual(50))
    val target = GreaterThan(50)

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }

  "Negating equals" should "result in LessThan OR GreaterThan" in {
    val original = NotNode(Equals(42))
    val target = OrNode(LessThan(42), GreaterThan(42))

    val actual = unitUnderTest.removeNegations(original)

    actual shouldBe target
  }
}

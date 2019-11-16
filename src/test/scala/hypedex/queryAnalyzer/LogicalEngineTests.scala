package hypedex.queryAnalyzer

import hypedex.queryAnalyzer.models.{AndNode, GreaterThan, LessThan, LessThanEqual, NotNode, OrNode}
import org.scalatest.{FlatSpec, Matchers}

class LogicalEngineTests extends FlatSpec with Matchers {
  val unitUnderTest = new LogicalEngine()

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
}

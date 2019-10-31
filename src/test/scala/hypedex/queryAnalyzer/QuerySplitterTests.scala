package hypedex.queryAnalyzer

import hypedex.logicalEngine.model.{GreaterThan, GreaterThanEqual, LessThan}
import org.scalatest.{FlatSpec, Matchers}

class QuerySplitterTests extends FlatSpec with Matchers {
  val queryAnalyzer = new QueryAnalyzer()
  "Simple AND clause" should "return without the clause" in {
    val clause = "x > 0 AND y < 10 AND -55 <= z"
    val ids = Set("x", "y", "z")
    val expectedResult = Map("x" -> Set(GreaterThan(0)), "y" -> Set(LessThan(10)), "z" -> Set(GreaterThanEqual(-55)))

    val tree = queryAnalyzer.createAST(clause)
    val result = queryAnalyzer.splitQueryCondition(tree, ids)

    expectedResult should equal(result)
  }
}

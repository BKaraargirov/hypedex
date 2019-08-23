package hypedex.queryAnalyzer

import org.scalatest.{FlatSpec, Matchers}

class QuerySplitterTests extends FlatSpec with Matchers {
  val queryAnalyzer = new QueryAnalyzer()
  "Simple AND clause" should "return without the clause" in {
    val clause = "x > 0 AND y < 10 AND z > -55"
    val ids = Set("x", "y", "z")
    val expectedResult = Map("x" -> "x>0", "y" -> "y<10", "z" -> "z>-55")

    val tree = queryAnalyzer.createAST(clause)
    val result = queryAnalyzer.splitQueryCondition(tree, ids)

    expectedResult should equal(result)
  }

}

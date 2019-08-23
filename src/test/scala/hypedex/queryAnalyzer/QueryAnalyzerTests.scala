package hypedex.queryAnalyzer

import org.scalatest.{FlatSpec, Matchers}

class QueryAnalyzerTests extends FlatSpec with Matchers {
  "Ids" should "be extracted correctly" in {
    val expectedResult = Set[String]("x", "a")
    val query = "x > 5 AND a < 15 OR x < -10"
    val queryAnalyzer = new QueryAnalyzer()

    val tree = queryAnalyzer.createAST(query)
    val actualResult = queryAnalyzer.getIds(tree)

    actualResult shouldEqual expectedResult
  }
}

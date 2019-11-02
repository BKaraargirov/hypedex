package hypedex.services

import hypedex.models.PartitionPredicate
import hypedex.queryAnalyzer.{IdExtractor, QueryDestructor}
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}

class QueryAnalysisService(
  private val syntaxTreeFactory: SyntaxTreeFactory,
  private val predicateFactory: PredicateFactory,
  private val sqlParser: SqlParser,
  private val idExtractor: IdExtractor) {
  def getPartitionPredicate(query: String): PartitionPredicate = {
    val whereClause = this.sqlParser.extractWhereClause(query)
    val logicalSyntaxTree = syntaxTreeFactory.createAST(whereClause)
    val predicateIds = getIds(logicalSyntaxTree)
    val basePredicates = splitQueryCondition(logicalSyntaxTree, predicateIds)
    predicateFactory.createPredicate(basePredicates)
  }


  def splitQueryCondition(tree: ParseTree, ids: Set[String]): QueryDestructor.Mapping = {
    val querySplitter = new QueryDestructor(ids)
    querySplitter.visit(tree)
  }

  def getIds(tree: ParseTree): Set[String] = {
    val walker = new ParseTreeWalker()
    walker.walk(idExtractor, tree)
    idExtractor.ids.toSet
  }
}

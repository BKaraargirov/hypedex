package hypedex.services

import hypedex.models.payloads.HypedexPayload
import hypedex.models.{DimensionPredicate, KDNode, PartitionNode, PartitionPredicate, TreeNode}
import hypedex.queryAnalyzer.{IdExtractor, QueryDestructor}
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}

class QueryAnalysisService[T <: HypedexPayload](
  private val syntaxTreeFactory: SyntaxTreeFactory,
  private val predicateFactory: PredicateFactory,
  private val sqlParser: SqlParser,
  private val idExtractor: IdExtractor) {
  def findSubset(root: TreeNode, filters: Map[String, DimensionPredicate]): List[PartitionNode[T]] = {
    def loop(node: TreeNode): List[PartitionNode[T]] = {
      if(root.isInstanceOf[PartitionNode[T]]) {
        return List(root.asInstanceOf[PartitionNode[T]])
      }

      val node = root.asInstanceOf[KDNode]

      if(filters.contains(node.dimensionName) == false) {
        findSubset(node.left, filters) ++ findSubset(node.right, filters)
      }

      val f = filters(node.dimensionName)

      // TODO: Need to support OR clauses
      // TODO: Is the second range needed
      val r = if(f.upperBound >= node.medianValue || f.isWithinRange(node.medianValue))
        findSubset(node.right, filters)
      else List()

      val l = if(f.lowerBound < node.medianValue)
        findSubset(node.left, filters)
      else List()

      r ++ l
    }

    loop(root)
  }

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

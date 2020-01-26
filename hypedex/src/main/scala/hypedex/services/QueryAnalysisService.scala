package hypedex.services

import hypedex.models.payloads.HypedexPayload
import hypedex.models.{DimensionPredicate, KDNode, PartitionNode, PartitionPredicate, TreeNode}
import hypedex.partitionConstructor
import hypedex.partitionConstructor.{DimensionPredicate, KDNode, PartitionNode, PartitionPredicate}
import hypedex.queryAnalyzer.{IdExtractor, QueryDestructor}
import org.antlr.v4.runtime.tree.{ParseTree, ParseTreeWalker}

class QueryAnalysisService[T <: HypedexPayload](
  private val syntaxTreeFactory: SyntaxTreeFactory,
  private val sqlParser: SqlParser,
  private val idExtractor: IdExtractor) {
  def findSubset(root: TreeNode, filters: PartitionPredicate): List[PartitionNode[T]] = {
    def dimensionPredicates = toDimensionPredicate(filters)

    def loop(node: TreeNode): List[PartitionNode[T]] = {
      if(root.isInstanceOf[PartitionNode[T]]) {
        return List(root.asInstanceOf[PartitionNode[T]])
      }

      val node = root.asInstanceOf[KDNode]

      if(filters.mapping.contains(node.dimensionName) == false) {
        findSubset(node.left, filters) ++ findSubset(node.right, filters)
      }

      val f = dimensionPredicates(node.dimensionName)

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

  def toDimensionPredicate(partitionPredicate: PartitionPredicate): Map[String, DimensionPredicate] = {
    partitionPredicate.mapping
      .map {
      case (dimName, treeNode) => (dimName, partitionConstructor.DimensionPredicate(dimName, treeNode))
    }
  }

  def getPartitionPredicate(query: String): PartitionPredicate = {
    val whereClause = this.sqlParser.extractWhereClause(query)
    val logicalSyntaxTree = syntaxTreeFactory.createAST(whereClause)
    val predicateIds = getIds(logicalSyntaxTree)
    val basePredicates = splitQueryCondition(logicalSyntaxTree, predicateIds)
    new PartitionPredicate(basePredicates)
  }

  def splitQueryCondition(tree: ParseTree, ids: Set[String]): QueryDestructor.Mapping = {
    val queryDestructorListener = new QueryDestructor(ids)
    ParseTreeWalker.DEFAULT.walk(queryDestructorListener, tree)
    queryDestructorListener.getPredicates()
  }

  def getIds(tree: ParseTree): Set[String] = {
    val walker = new ParseTreeWalker()
    walker.walk(idExtractor, tree)
    idExtractor.ids.toSet
  }
}

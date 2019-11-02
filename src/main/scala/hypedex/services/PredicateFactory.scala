package hypedex.services

import hypedex.models.{DimensionPredicate, PartitionPredicate}
import hypedex.queryAnalyzer.models.LogicalExpression

class PredicateFactory {
  def createPredicate(rawPredicates: Map[String, Set[LogicalExpression]]): PartitionPredicate = {
    val dimensionPredicates = rawPredicates
      .map(rp => rp._1 -> DimensionPredicate(dimensionName = rp._1, conditions = rp._2))
    new PartitionPredicate(dimensionPredicates)
  }
}

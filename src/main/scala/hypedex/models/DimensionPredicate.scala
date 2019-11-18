package hypedex.models

import hypedex.queryAnalyzer.LogicalEngine
import hypedex.queryAnalyzer.models.{AndNode, BooleanNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression, LogicalTreeNode, OrNode}

/**
  * Contains the acceptable values
  */
case class DimensionPredicate(
   dimensionName: String,
   private val conditions: BooleanNode
) {
  val upperBound: Double = LogicalEngine.findUpperBound(conditions)
  val lowerBound: Double = LogicalEngine.findLowerBound(conditions)

  def isWithinRange(point: Double): Boolean = {
    def loop(node: LogicalTreeNode) : Boolean= {
      node match {
        case AndNode(children) => children.forall(loop)
        case OrNode(children) => children.exists(loop)
        case leaf: LogicalExpression => leaf.isWithinRange(point)
      }
    }

    loop(conditions)
  }

  def hasIntersection(boundary: PartitionBoundary): Boolean = {
    def loop(node: LogicalTreeNode): Boolean = {
      node match {
        case AndNode(children) => children.exists(loop)
        case OrNode(children) => children.exists(loop)
        case leaf: LogicalExpression => boundary.doesIntersectionExists(leaf)
      }
    }

    loop(this.conditions)
  }
}

package hypedex.logicalEngine.model

import scala.collection.immutable.SortedSet


/**
  * This class
  */
case class AxisExpression(
  private val conditions: Set[LogicalExpression] = Set[LogicalExpression]()
) {
  // TODO: There may be redundant expressions, may be optimised at some point
  def createCheck(): Double => Boolean =
    value => this.conditions.forall(_.isWithinRange(value))
}

package hypedex.logicalEngine.model

import scala.collection.immutable.SortedSet


/**
  * Contains the acceptable values
  */
case class AccumulatedFilter(
  private val conditions: Set[LogicalExpression] = Set[LogicalExpression]()
) {
  val upperBound: Double = findUpperBound(conditions)
  val lowerBound: Double = findLowerBound(conditions)

  def isWithinRange(point: Double): Boolean = this.conditions.forall(_.isWithinRange(point))

  def findUpperBound(expressions: Set[LogicalExpression]): Double = {
    val rightMostExpression = expressions.maxBy(exp => (exp.value, exp.direction))

    rightMostExpression match {
      case GreaterThan(_) => Double.PositiveInfinity
      case GreaterThanEqual(_) => Double.PositiveInfinity
      case Equals(value) => value
      case LessThan(value) => value - 1
      case LessThanEqual(value) => value
    }
  }

  def findLowerBound(expressions: Set[LogicalExpression]): Double = {
    val leftMostExpression = expressions.minBy(exp => (exp.value, exp.direction))

    leftMostExpression match {
      case GreaterThan(value) => value + 1
      case GreaterThanEqual(value) => value
      case Equals(value) => value
      case LessThan(_) => Double.NegativeInfinity
      case LessThanEqual(_) => Double.NegativeInfinity
    }
  }
}

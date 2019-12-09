package hypedex.queryAnalyzer

import hypedex.queryAnalyzer.models.{AndNode, BooleanNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression, LogicalTreeNode, NotNode, OrNode}

object LogicalEngine {

 def removeNegations(logicalTreeRoot: LogicalTreeNode): LogicalTreeNode = {
    def loop(node: LogicalTreeNode, shouldNegate: Boolean): LogicalTreeNode = {
       node match {
        case AndNode(children) if shouldNegate => OrNode(children.map(loop(_, shouldNegate)))
        case AndNode(children) if !shouldNegate => AndNode(children.map(loop(_, shouldNegate)))
        case OrNode(children) if shouldNegate => AndNode(children.map(loop(_, shouldNegate)))
        case OrNode(children) if !shouldNegate => OrNode(children.map(loop(_, shouldNegate)))
        case NotNode(child) => loop(child, !shouldNegate)

        case le: LogicalExpression if !shouldNegate => le

        case Equals(eq) if shouldNegate => OrNode(List(LessThan(eq), GreaterThan(eq)))
        case GreaterThan(gt) if shouldNegate => LessThanEqual(gt)
        case GreaterThanEqual(gte) if shouldNegate => LessThan(gte)
        case LessThan(lt) if shouldNegate => GreaterThanEqual(lt)
        case LessThanEqual(lte) if shouldNegate => GreaterThan(lte)
      }
    }

    loop(logicalTreeRoot, false)
  }

  def findUpperBound(root: LogicalTreeNode): Double = {
    val rightMostExpression = findLeafs(root).maxBy(exp => (exp.value, exp.direction))

    rightMostExpression match {
      case GreaterThan(_) => Double.PositiveInfinity
      case GreaterThanEqual(_) => Double.PositiveInfinity
      case Equals(value) => value
      case LessThan(value) => value - 1
      case LessThanEqual(value) => value
    }
  }

  def findLowerBound(root: LogicalTreeNode): Double = {
    val leftMostExpression = findLeafs(root).minBy(exp => (exp.value, exp.direction))

    leftMostExpression match {
      case GreaterThan(value) => value + 1
      case GreaterThanEqual(value) => value
      case Equals(value) => value
      case LessThan(_) => Double.NegativeInfinity
      case LessThanEqual(_) => Double.NegativeInfinity
    }
  }

  def findLeafs(root: LogicalTreeNode): List[LogicalExpression] = root match {
    case AndNode(children) => children.flatMap(findLeafs)
    case OrNode(children) => children.flatMap(findLeafs)
    case leaf: LogicalExpression => List(leaf)
  }
}

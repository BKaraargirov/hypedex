package hypedex.queryAnalyzer

import hypedex.queryAnalyzer.models.{AndNode, BooleanNode, Equals, GreaterThan, GreaterThanEqual, LessThan, LessThanEqual, LogicalExpression, LogicalTreeNode, NotNode, OrNode}

class LogicalEngine {
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
}

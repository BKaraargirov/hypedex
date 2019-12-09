package hypedex.queryAnalyzer.models

sealed class BooleanNode() extends LogicalTreeNode {}

case class AndNode (
  children: List[LogicalTreeNode]
) extends BooleanNode {
  def this(l: LogicalTreeNode*) = this(l.toList)
}

object AndNode {
  def apply(l: LogicalTreeNode*) = new AndNode(l.toList)
}

case class OrNode (
  children: List[LogicalTreeNode]
) extends BooleanNode {
  def this(l: LogicalTreeNode*) = this(l.toList)
}

object OrNode {
  def apply(l: LogicalTreeNode*) = new OrNode(l.toList)
}

case class NotNode (
  child: LogicalTreeNode
) extends BooleanNode()

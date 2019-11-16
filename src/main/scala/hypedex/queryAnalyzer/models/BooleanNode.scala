package hypedex.queryAnalyzer.models

sealed class BooleanNode() extends LogicalTreeNode {}

case class AndNode (
  children: List[LogicalTreeNode]
) extends BooleanNode

case class OrNode (
  children: List[LogicalTreeNode]
) extends BooleanNode

case class NotNode (
  child: LogicalTreeNode
) extends BooleanNode()

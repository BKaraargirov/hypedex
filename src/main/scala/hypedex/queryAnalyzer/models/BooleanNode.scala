package hypedex.queryAnalyzer.models

sealed class BooleanNode(
  val children: List[LogicalTreeNode]
) extends LogicalTreeNode

case class AndNode (
  override val children: List[LogicalTreeNode]
) extends BooleanNode(children)

case class OrNode (
  override val children: List[LogicalTreeNode]
) extends BooleanNode(children)

case class NotNode (
  override val children: List[LogicalTreeNode]
) extends BooleanNode(children)

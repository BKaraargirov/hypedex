package hypedex.models

case class KDNode[+NodeValue](
  dimensionName: String,
  value: NodeValue,
  left: Option[KDNode[NodeValue]],
  right: Option[KDNode[NodeValue]]
)

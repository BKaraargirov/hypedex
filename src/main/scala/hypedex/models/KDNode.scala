package hypedex.models

import hypedex.models.payloads.HypedexPayload

case class KDNode[+NodeValue <: HypedexPayload](
  dimensionName: String,
  value: NodeValue,
  left: Option[KDNode[NodeValue]],
  right: Option[KDNode[NodeValue]]
)

package hypedex.models

import hypedex.models.payloads.HypedexPayload

case class Metadata[+NodeValue <: HypedexPayload] (
 id: String,
 distanceFunction: (Double, Double) => Double,
 treeRoot: KDNode[NodeValue]
 )

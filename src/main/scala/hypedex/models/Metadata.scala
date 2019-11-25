package hypedex.models

import hypedex.models.payloads.HypedexPayload

case class Metadata (
 id: String,
 distanceFunction: (Double, Double) => Double,
 treeRoot: TreeNode
 )

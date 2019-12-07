package hypedex.models

import hypedex.models.payloads.HypedexPayload

@SerialVersionUID(4602871170332808701L)
case class Metadata (
 id: String,
 distanceFunction: (Double, Double) => Double,
 treeRoot: TreeNode,
 dataBaseDir: String
 )

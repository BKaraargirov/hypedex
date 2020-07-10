package hypedex.models

import hypedex.models.payloads.HypedexPayload

@SerialVersionUID(4602871170332808701L)
case class Metadata[T] (
 id: String,
 distanceFunction: (T, T) => Double,
 treeRoot: TreeNode,
 dataBaseDir: String
 )

package hypedex.services

import hypedex.models.TreeNode
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.{EmptyNode, KDNode, PartitionNode}

class TreeUtils {
  /**
    * Retrieves all child partiotion nodes as a list, order is not guaranteed
    */
  def getPartitions[T<: HypedexPayload](root: TreeNode): List[PartitionNode[T]] = root match {
    case _: EmptyNode => List[PartitionNode[T]]()
    case x: PartitionNode[T] => List(x)
    case x: KDNode[T] => getPartitions[T](x.left) ++ getPartitions[T](x.right)
    case _ => throw new Exception(s"unsupported node type")
  }
}

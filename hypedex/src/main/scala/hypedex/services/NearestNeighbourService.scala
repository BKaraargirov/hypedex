package hypedex.services

import hypedex.models.{Metadata, TreeNode}
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.{EmptyNode, KDNode, PartitionNode}
import org.apache.spark.sql.Dataset

class NearestNeighbourService[T <: HypedexPayload](treeUtils: TreeUtils) {
  def findPartitionCandidates(referencePoint: T, metadata: Metadata[T]): List[PartitionNode[T]] = {

    def loop(currentNode: TreeNode, currentBestDistance: Double): TreeNode = currentNode match {
      case x: EmptyNode => EmptyNode()
      case x: PartitionNode[T] => EmptyNode()
      case x: KDNode[T] => {
        val currentDistance = metadata.distanceFunction.apply(referencePoint, x.medianPoint)
          if(currentDistance > currentBestDistance) EmptyNode()
          else  {
            val leftResult = loop(x.left, currentDistance)
            val rightResult = loop(x.right, currentDistance)

            (leftResult, rightResult) match {
              case (l : EmptyNode, r : EmptyNode) => currentNode
              case (l, r : EmptyNode) => l
              case (l: EmptyNode, r) => r
              case (_, _) => currentNode
            }
          }
      }
    }

    val parent = loop(metadata.treeRoot, Double.MaxValue)
    treeUtils.getPartitions(parent)
  }

  /**
    * Brute force
    * @param ds
    * @param referencePoint
    * @param metadata
    * @return
    */
  def findNearestNeighbour(ds: Dataset[T],
                           referencePoint: T,
                           metadata: Metadata[T],
                           distanceLimit: Option[Double] = Option.empty
                          ): T = {
    val distanceFunction = metadata.distanceFunction
    var mapped = ds
      .rdd
      .map(point => (point, distanceFunction(referencePoint, point)))

      if(distanceLimit.isDefined)
        mapped = mapped.filter(_._2 <= distanceLimit.get)

    mapped
      .sortBy(_._2)
      .first()
      ._1
  }
}

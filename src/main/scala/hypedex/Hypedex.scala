package hypedex

import hypedex.logicalEngine.model.AccumulatedFilter
import hypedex.models.payloads.HypedexPayload
import hypedex.models.{KDNode, Metadata, PartitionNode, TreeNode}
import hypedex.storage.TMetadataStore
import org.apache.spark.sql.Dataset

class Hypedex[T <: HypedexPayload](
  private val metadataStore: TMetadataStore[Metadata]
) {
  def loadData(metadataId: String, whereClause: String): Dataset[Any] = {
    val metadata = this.metadataStore.getMetadataById(metadataId)



    ???
  }

  //TODO: make parralel
  def findSubset(root: TreeNode, filters: Map[String, AccumulatedFilter]): List[PartitionNode[T]] = {
    if(root.isInstanceOf[PartitionNode[T]]) {
      return List(root.asInstanceOf[PartitionNode[T]])
    }

    val node = root.asInstanceOf[KDNode]

    if(filters.contains(node.dimensionName) == false) {
      findSubset(node.left, filters) ++ findSubset(node.right, filters)
    }

    val f = filters(node.dimensionName)

    // TODO: Need to support OR clauses
    // TODO: Is the second range needed
    val r = if(f.upperBound >= node.medianValue || f.isWithinRange(node.medianValue))
      findSubset(node.right, filters)
     else List()

    val l = if(f.lowerBound < node.medianValue)
      findSubset(node.left, filters)
    else List()

    r ++ l
  }
}

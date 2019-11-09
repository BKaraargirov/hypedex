package hypedex

import hypedex.models.payloads.HypedexPayload
import hypedex.models.{DimensionPredicate, KDNode, Metadata, PartitionNode, TreeNode}
import hypedex.storage.TMetadataStore
import org.apache.spark.sql.{DataFrame, Dataset, SQLContext}

class Hypedex[T <: HypedexPayload](
  private val metadataStore: TMetadataStore[Metadata],
  private val sqlContext: SQLContext
) {

  def loadData(partitions: List[PartitionNode[T]]): DataFrame = {
    sqlContext.read.parquet(partitions.map(_.dataUrl):_*)
  }

  //TODO: make parralel
  def findSubset(root: TreeNode, filters: Map[String, DimensionPredicate]): List[PartitionNode[T]] = {
    def loop(node: TreeNode): List[PartitionNode[T]] = {
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

    loop(root)
//      .filter(node => {
//      filters.map {
//        case (dimName, predicates: DimensionPredicate) => predicates.hasIntersection(node.boundary(dimName))
//      }.forall(_ == true)
//    })
  }
}

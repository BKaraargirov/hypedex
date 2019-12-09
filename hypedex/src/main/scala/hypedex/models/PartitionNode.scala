package hypedex.models

import hypedex.models.payloads.HypedexPayload
import org.apache.spark.sql.Dataset

/**
  * Represents a data partition. It holds the dataframe when the tree is being build and the
  * data url for when the tree is being traversed to get the data.
  *
  * @param id will act as file name aswell.
  * @param boundary Specifies the exact frame of the data located within the partition.
  * @param data to be stored
  */
case class PartitionNode[T <: HypedexPayload](
  id: String,
  boundary: Map[String, PartitionBoundary],
  @transient data: Option[Dataset[T]]
) extends TreeNode {
  def removeData(): PartitionNode[T] = {
    this.data.get.unpersist()
    PartitionNode(this.id, this.boundary, Option.empty)
  }
}

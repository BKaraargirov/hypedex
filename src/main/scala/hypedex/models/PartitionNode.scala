package hypedex.models

import hypedex.models.payloads.HypedexPayload
import org.apache.spark.sql.Dataset

/**
  * Represents a data partition. It holds the dataframe when the tree is being build and the
  * data url for when the tree is being traversed to get the data.
  *
  * @param id will act as file name aswell.
  * @param dataUrl The space where the data is being stored. Currently only hdfs is supported
  * @param data to be stored
  */
case class PartitionNode[T <: HypedexPayload](
  id: String,
  dataUrl: String,
  @transient data: Option[Dataset[T]]
) extends TreeNode

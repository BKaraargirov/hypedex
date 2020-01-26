package hypedex.storage

import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.PartitionNode
import org.apache.spark.sql.{DataFrame, Dataset, Row}

trait PartitionStore[T <: HypedexPayload] {
  def save(partition: PartitionNode[T], url: String): Unit
  def load(partitionNodes: List[PartitionNode[T]], url: String): DataFrame
}

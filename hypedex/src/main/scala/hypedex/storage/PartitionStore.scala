package hypedex.storage

import hypedex.models.PartitionNode
import hypedex.models.payloads.HypedexPayload

trait PartitionStore[T <: HypedexPayload] {
  def save(partition: PartitionNode[T], url: String): Unit
}

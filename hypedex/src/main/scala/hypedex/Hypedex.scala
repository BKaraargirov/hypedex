package hypedex

import hypedex.models.payloads.HypedexPayload
import hypedex.models.{Metadata, TreeNode}
import hypedex.storage.TMetadataStore
import org.apache.spark.sql.{DataFrame, Dataset, SQLContext}

class Hypedex[T <: HypedexPayload](
  private val metadataStore: TMetadataStore[Metadata],
  private val sqlContext: SQLContext
) {

}

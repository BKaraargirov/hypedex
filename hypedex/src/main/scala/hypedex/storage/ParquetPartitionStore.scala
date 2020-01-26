package hypedex.storage
import java.nio.file.Paths

import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.PartitionNode
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

case class ParquetPartitionStore[T <: HypedexPayload](private val session: SparkSession) extends PartitionStore[T] {
  override def save(partition: PartitionNode[T], hdfsUrl: String): Unit = {
    partition.data.get.write.parquet(Paths.get(hdfsUrl, partition.id).toString)
  }

  override def load(partitionNodes: List[PartitionNode[T]], url: String): DataFrame =
    session.sqlContext
    .read
    .parquet(partitionNodes.map(node => s"${url}/${node.id}"):_*)

}

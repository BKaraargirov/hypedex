package hypedex.storage
import java.nio.file.Paths

import hypedex.models.PartitionNode
import hypedex.models.payloads.HypedexPayload
import org.apache.spark.sql.SparkSession

case class ParquetPartitionStore[T <: HypedexPayload](private val session: SparkSession) extends PartitionStore[T] {
  override def save(partition: PartitionNode[T]): Unit = {
    partition.data.get.write.parquet(Paths.get(partition.dataUrl, partition.id).toString)
  }
}

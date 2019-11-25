package hypedex.services

import java.util.UUID

import hypedex.models.{KDNode, Metadata, PartitionNode, TreeNode}
import hypedex.models.payloads.HypedexPayload
import hypedex.storage.{PartitionStore, TMetadataStore}
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable

class DataManipulationService[T <: HypedexPayload](
  session: SparkSession,
  partitionRepository: PartitionStore[T],
  metadataStore: TMetadataStore[Metadata],
  kdTreeBuilder: KDTreeBuilder[T]
) {
  def createPartitions(
    originalDataDir: String,
    originalFilePattern: String,
    targetDataDir: String,
    mapper: Row => T,
    distanceFunction: (Double, Double) => Double
  ): Metadata = {
    import session.implicits._

    val ds = session.read.option("header", "true")
      .csv(s"${originalDataDir}/${originalFilePattern}")
      .map(mapper)

    val kdTree = kdTreeBuilder.buildTree(ds, 4)
    persistParquets(kdTree, targetDataDir)
    val metadata = Metadata(UUID.randomUUID().toString, distanceFunction, kdTree)
    this.metadataStore.saveMetadata(metadata)

    metadata
  }

  def persistParquets(kdTree: TreeNode, targetDir: String) = {
    val queue = mutable.Queue(kdTree)

    while(queue.isEmpty == false) {
      val currentNode = queue.dequeue()

      currentNode match {
        case partition: PartitionNode[T] => this.partitionRepository.save(partition, targetDir)
        case node: KDNode => queue.enqueue(node.left, node.right)
        case _ => _
      }
    }
  }
}

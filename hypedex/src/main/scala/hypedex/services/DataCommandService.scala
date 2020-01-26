package hypedex.services

import java.util.UUID

import hypedex.models.{CalculationWrapper, KDNode, Metadata, PartitionNode, TreeNode}
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.{CalculationWrapper, KDNode, PartitionNode}
import hypedex.storage.{PartitionStore, TMetadataStore}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Row, SparkSession}

import scala.collection.mutable

class DataCommandService[T <: HypedexPayload](
  session: SparkSession,
  partitionRepository: PartitionStore[T],
  metadataStore: TMetadataStore[Metadata],
  kdTreeBuilder: KDTreeBuilder[T],
  mapper: Row => T
) {
  def createPartitions(
    originalDataDir: String,
    originalFilePattern: String,
    targetDataDir: String,
    distanceFunction: (Double, Double) => Double,
    depth: Int =  3
  )(implicit enc: Encoder[T], calcWrapperEncoder: Encoder[CalculationWrapper]): Metadata = {
    val ds = session.read.option("header", "true")
      .parquet(s"${originalDataDir}/${originalFilePattern}")
      .map(mapper)

    ds.persist()
    val kdTree = kdTreeBuilder.buildTree(ds, depth)(calcWrapperEncoder)
    persistParquets(kdTree, targetDataDir)
    ds.unpersist()
    val metadata = Metadata(UUID.randomUUID().toString, distanceFunction, kdTree, targetDataDir)
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
      }
    }
  }

}

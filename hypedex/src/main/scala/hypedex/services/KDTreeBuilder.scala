package hypedex.services

import java.util.UUID

import hypedex.models.TreeNode
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.{CalculationWrapper, KDNode, PartitionBoundary, PartitionNode}
import hypedex.queryAnalyzer.models.{GreaterThanEqual, LessThan}
import hypedex.storage.{PartitionStore, MetadataRepository}
import org.apache.spark.sql.{Dataset, Encoder, SQLContext}

/**
  * Responsible for building the tree metadata and partitions
  *
  * @param dimensionOrder The order in which dimensions should be used
  */
class KDTreeBuilder[T <: HypedexPayload](
  val sqlContext: SQLContext,
  dimensionOrder: Array[String],
  splitErrorTolerance: Double = 0.25
) {
  // Should always be equal the the holder value of CalculationWrapper
  private val WRAPPER_PROPERTY = "x"
  private val metadataSubfolder = "metadata" //TODO: extract to config

  /**
    * @param data  to be partitioned
    * @param depth of the tree
    * @return
    */
  def buildTree(data: Dataset[T], depth: Int)
               (implicit encoder: Encoder[CalculationWrapper]): TreeNode = {
    def loop(data: Dataset[T], currentDepth: Int, boundary: Map[String, PartitionBoundary]): TreeNode = {
      if (currentDepth >= depth) {
        PartitionNode(UUID.randomUUID().toString, boundary, Option(data))
      }
      else {
        val dimName = getTargetDimension(currentDepth, dimensionOrder)
        val (left, right, splitPoint, point) = split(data, dimName)

        val leftBoundary= boundary + (dimName -> boundary(dimName).updateBoundary(LessThan(splitPoint)))
        val rightBoundary = boundary + (dimName -> boundary(dimName).updateBoundary(GreaterThanEqual(splitPoint)))

        KDNode(
          dimensionName = dimName,
          medianValue = splitPoint,
          medianPoint = point,
          left = loop(left, currentDepth + 1, leftBoundary),
          right = loop(right, currentDepth + 1, rightBoundary)
        )
      }
    }

    val defaultBoundaries = dimensionOrder.map(dim => dim -> PartitionBoundary(dim)).toMap
    loop(data, 0, defaultBoundaries)
  }

  /**
    * Split a data set into two based on the median
    *
    * @param data          to be split
    * @param dimensionName of the dimension that will be used to split the data on
    * @return
    */
  def split(data: Dataset[T], dimensionName: String)
           (implicit encoder: Encoder[CalculationWrapper]): (Dataset[T], Dataset[T], Double, T) = {
    val splitPoint = data
      .map(p => CalculationWrapper(p.getDimensions()(dimensionName)))
      .stat.approxQuantile(WRAPPER_PROPERTY, Array(0.5), splitErrorTolerance).head


    val left = data.filter((p: HypedexPayload) => p.getDimensions()(dimensionName) < splitPoint)

    val right = data.filter((p: HypedexPayload) => p.getDimensions()(dimensionName) >= splitPoint)

    val mid = data.filter((p: HypedexPayload) => p.getDimensions()(dimensionName) == splitPoint)

    (left, right, splitPoint, mid.first())
  }

  /**
    * Get the next dimension to be used in the tree builder proceess
    * @param depth current depth of the tree
    * @param dimensions The list of dimensions that will be used
    * @return the name of the next dimension
    */
  def getTargetDimension(depth: Int, dimensions: Array[String]): String =
    dimensions(depth % dimensions.length)
}


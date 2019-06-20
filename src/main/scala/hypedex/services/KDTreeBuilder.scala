package hypedex.services

import java.util.UUID

import hypedex.models.{KDNode, PartitionNode, TreeNode}
import hypedex.models.payloads.HypedexPayload
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Dataset, SQLContext}

/**
  * Responsible for building the tree metadata and partitions
  *
  * @param dimensionOrder The order in which dimensions should be used
  */
class KDTreeBuilder[T <: HypedexPayload](
  val sqlContext: SQLContext,
  dimensionOrder: Array[String],
  hdfsUrl: String //TODO: Get rid of this.
) {
  // Should always be equal the the holder value of CalculationWrapper
  private val WRAPPER_PROPERTY = "x"


  /**
    * @param data  to be partitioned
    * @param depth of the tree
    * @return
    */
  def buildTree(data: Dataset[T], depth: Int): TreeNode = {
    def loop(data: Dataset[T], currentDepth: Int): TreeNode = {
      if (currentDepth > depth) {
        PartitionNode(UUID.randomUUID().toString, hdfsUrl, Option(data))
      }
      else {
        val dimName = getTargetDimension(currentDepth, dimensionOrder)
        val (left, right, splitPoint) = split(data, dimName)

        KDNode(
          dimensionName = dimName,
          medianValue = splitPoint,
          left = loop(left, currentDepth + 1),
          right = loop(right, currentDepth + 1)
        )
      }
    }

    loop(data, 0)
  }

  /**
    * Split a data set into two based on the median
    *
    * @param data          to be split
    * @param dimensionName of the dimension that will be used to split the data on
    * @return
    */
  def split(data: Dataset[T], dimensionName: String): (Dataset[T], Dataset[T], Double) = {
    import sqlContext.implicits._

    val splitPoint = data
      .map(p => CalculationWrapper(p.getDimensions()(dimensionName)))
      .stat.approxQuantile(WRAPPER_PROPERTY, Array(0.5), 0).head


    val left = data.filter((p: HypedexPayload) => p.getDimensions()(dimensionName) < splitPoint)

    val right = data.filter((p: HypedexPayload) => p.getDimensions()(dimensionName) >= splitPoint)

    (left, right, splitPoint)
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

/**
  * The class is used so the stat.approximateQuantile can know where the value is.
  * Spark cannot serialize it when it's an inner class
  *
  * @param x is a holder
  */
case class CalculationWrapper(x: Double)

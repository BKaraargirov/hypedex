package hypedex.services

import hypedex.models.KDNode
import hypedex.models.payloads.HypedexPayload
import org.apache.spark.SparkContext
import org.apache.spark.sql.{Dataset, SQLContext}

/**
  * Responsible for building the tree metadata and partitions
  * @param dimensionOrder The order in which dimensions should be used
  */
class KDTreeBuilder[T <: HypedexPayload](val sqlContext: SQLContext)(
  dimensionOrder: Array[String]
) {
  // Should always be equal the the holder value of CalculationWrapper
  private val WRAPPER_PROPERTY = "x"

  /**
    * The class is used so the stat.approximateQuantile can know where the value is
    * @param x is a holder
    */
  private case class CalculationWrapper(x: Double)

  /**
    * @param data to be partitioned
    * @param depth of the tree
    * @return
    */
  def buildTree(data: Dataset[T], depth: Int): KDNode[T] = {
    ???
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


    val left = data.filter(p => p.getDimensions()(dimensionName) < splitPoint)

    val right = data.filter(p => p.getDimensions()(dimensionName) >= splitPoint)

    (left, right, splitPoint)
  }
}
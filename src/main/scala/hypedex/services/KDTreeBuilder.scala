package hypedex.services

import hypedex.models.payloads.HypedexPayload
import org.apache.spark.sql.Dataset

/**
  * Responsible for building the tree metadata and partitions
  * @param dimensionOrder The order in which dimensions should be used
  */
class KDTreeBuilder(
  dimensionOrder: Array[String]
) {
  def buildTree(data: Dataset[HypedexPayload]) = ???

  def compare[T](x: T, y: T)(implicit n: Numeric[T]) = {
    import n._

    x.toDouble()
  }
}
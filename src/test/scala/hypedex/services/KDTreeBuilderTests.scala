package hypedex.services

import hypedex.models.payloads.OneDimensionalPayload
import hypedex.testUtils.{DataLoader, SparkContextHolder}
import org.apache.spark.sql.Dataset
import org.scalatest.{FlatSpec, Matchers}

class KDTreeBuilderTests extends FlatSpec with Matchers {
  val session = SparkContextHolder.getSession()
  val treeBuilder = new KDTreeBuilder[OneDimensionalPayload](session.sqlContext, Array("x", "y", "z"))

  "Splitting data" should "be on the median" in {
    import session.sqlContext.implicits._

    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(5), wrap(-22), wrap(150))
    val dataset: Dataset[OneDimensionalPayload] = session.sqlContext.createDataset(data)

    val split = treeBuilder.split(dataset, "x")

    split._3 should equal(5)
    split._1.count() should equal(4)
    split._2.count() should equal(5)
  }
}

package hypedex.services

import hypedex.models.payloads.OneDimensionalPayload
import hypedex.testUtils.SparkContextHolder
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Matchers}

class KDTreeBuilderTests extends FlatSpec with Matchers {
  "Splitting data" should "be on the median" in {
    val session = SparkContextHolder.getSession()
    import session.sqlContext.implicits._

    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(5), wrap(-22), wrap(150))
    val dataset = session.sqlContext.createDataset(data)
  }
}

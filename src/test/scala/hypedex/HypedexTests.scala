package hypedex

import hypedex.models.DimensionPredicate
import hypedex.models.payloads.OneDimensionalPayload
import hypedex.queryAnalyzer.models.{GreaterThan, LessThan}
import hypedex.services.KDTreeBuilder
import hypedex.testUtils.SparkContextHolder
import org.apache.spark.sql.Dataset
import org.scalatest.{FlatSpec, Matchers}

class HypedexTests extends FlatSpec with Matchers {
  val session = SparkContextHolder.getSession()
  val dimensArray = Array("x", "y", "z")
  val treeBuilder = new KDTreeBuilder[OneDimensionalPayload](session.sqlContext, dimensArray, "./")

  "Filter" should "return to one partition" in {
    import session.sqlContext.implicits._

    val hypedex = new Hypedex[OneDimensionalPayload](null)
    val treeBuilder = new KDTreeBuilder[OneDimensionalPayload](session.sqlContext, Array("x"), "./")
    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(5), wrap(-22), wrap(150))
    val dataset: Dataset[OneDimensionalPayload] = session.sqlContext.createDataset(data)

    val root = treeBuilder.buildTree(dataset, 2)

    val filters = DimensionPredicate("x", Set(GreaterThan(140)))

    val result = hypedex.findSubset(root, Map("x" -> filters))

    result.size shouldBe 1
    result.head.data.get.collect()
  }

  "Filter 2" should "return to one partition" in {
    import session.sqlContext.implicits._

    val hypedex = new Hypedex[OneDimensionalPayload](null)
    val treeBuilder = new KDTreeBuilder[OneDimensionalPayload](session.sqlContext, Array("x"), "./")
    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(6), wrap(11), wrap(5), wrap(-22), wrap(150))
    val dataset: Dataset[OneDimensionalPayload] = session.sqlContext.createDataset(data)

    val root = treeBuilder.buildTree(dataset, 2)

    val filters = DimensionPredicate("x", Set(GreaterThan(15), LessThan(90)))

    val result = hypedex.findSubset(root, Map("x" -> filters))

    result.size shouldBe 1
    result.head.data.get.collect()
  }

}

package hypedex.services

import hypedex.models.payloads.OneDimensionalPayload
import hypedex.partitionConstructor.{KDNode, PartitionNode}
import hypedex.testUtils.{DataLoader, SparkContextHolder}
import org.apache.spark.sql.Dataset
import org.scalatest.{FlatSpec, Ignore, Matchers}

@Ignore
class KDTreeBuilderTests extends FlatSpec with Matchers {
  val session = SparkContextHolder.getSession()
  val dimensArray = Array("x", "y", "z")
  val splitErrorTolerance = 0.0
  val treeBuilder = new KDTreeBuilder[OneDimensionalPayload](session.sqlContext, dimensArray, splitErrorTolerance)

  "Splitting data" should "be on the median" in {
    import session.sqlContext.implicits._


    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(5), wrap(-22), wrap(150))
    val dataset: Dataset[OneDimensionalPayload] = session.sqlContext.createDataset(data)

    val split = treeBuilder.split(data = dataset, dimensionName ="x")

    split._3 should equal(5)
    split._1.count() should equal(4)
    split._2.count() should equal(5)
  }

  "Root level" should "return the first element" in {
    val depth = 0
    val expectedDimension = "x"

    val actualResult = treeBuilder.getTargetDimension(depth, dimensArray)

    actualResult should equal(expectedDimension)
  }

  "2rd level" should "return the last element" in {
    val depth = 2
    val expectedResult = "z"

    val actualResult = treeBuilder.getTargetDimension(depth, dimensArray)

    actualResult should equal(actualResult)
  }

  "3rd level" should "return the first element again" in {
    val depth = 3
    val expectedResult = "x"

    val actualResult = treeBuilder.getTargetDimension(depth, dimensArray)

    actualResult should equal(expectedResult)
  }

  "Build tree" should "be working" in {
    import session.sqlContext.implicits._

    val wrap = OneDimensionalPayload.bindDimension("x")
    val data = Array(wrap(1), wrap(2), wrap(15), wrap(90), wrap(4), wrap(5), wrap(5), wrap(-22), wrap(150))
    val dataset: Dataset[OneDimensionalPayload] = session.sqlContext.createDataset(data)

    val root = treeBuilder.buildTree(dataset, 2)

    val p1 = root.asInstanceOf[KDNode[OneDimensionalPayload]].left.asInstanceOf[KDNode[OneDimensionalPayload]].left.asInstanceOf[PartitionNode[OneDimensionalPayload]]
    val p2 = root.asInstanceOf[KDNode[OneDimensionalPayload]].right.asInstanceOf[KDNode[OneDimensionalPayload]].right.asInstanceOf[PartitionNode[OneDimensionalPayload]]

    val base = root.asInstanceOf[KDNode[OneDimensionalPayload]].medianValue
    val l1 = root.asInstanceOf[KDNode[OneDimensionalPayload]].left.asInstanceOf[KDNode[OneDimensionalPayload]].medianValue
    val r1 = root.asInstanceOf[KDNode[OneDimensionalPayload]].right.asInstanceOf[KDNode[OneDimensionalPayload]].medianValue


    p1.isInstanceOf[PartitionNode[OneDimensionalPayload]] shouldEqual true
    p2.isInstanceOf[PartitionNode[OneDimensionalPayload]] shouldEqual true


    dataset.filter(p => p.value < l1).count shouldEqual p1.data.get.count()
    dataset.filter(p => p.value >= r1).count shouldEqual p2.data.get.count()
  }
}

package hypedex.services

import hypedex.models.{KDNode, Metadata, PartitionNode, TreeNode}
import hypedex.models.payloads.HypedexPayload
import hypedex.storage.{BasicMetadataStore, ParquetPartitionStore}
import hypedex.testUtils.SparkContextHolder
import org.apache.spark.sql.{Dataset, Row}
import org.scalatest.FlatSpec

case class AirQuality(
                       sensor_id: String,
                       location: String,
                       lat: String,
                       lon: String,
                       timestamp: String,
                       P1: Double,
                       P2: Double
                     ) extends HypedexPayload {
  override def getDimensions() = Map("P1" -> this.P1, "P2" -> this.P2)
}

class KDBuilderPerformanceTest extends FlatSpec {
  val session = SparkContextHolder.getSession()
  val baseDir = "/Users/silver/Documents/nbu/sofia-air-quality-dataset"
  val metadataDir = s"${baseDir}/metadata"
  val partitionStore = ParquetPartitionStore[AirQuality](session)
  val metadataStore = new BasicMetadataStore[Metadata](metadataDir)

  "It" should "run" in {
    import session.implicits._

    val dimensArray = Array("P1", "P2")
    val treeBuilder = new KDTreeBuilder[AirQuality](session.sqlContext, dimensArray)
    val dataManipulationService = new DataManipulationService[AirQuality](session, partitionStore, metadataStore, treeBuilder)

    val depth = 2

    dataManipulationService.createPartitions(
      baseDir,
      "*2019*sds*.csv",
      s"${baseDir}/kd-tree",
      {
        import session.implicits._

        r: Row => AirQuality(
        r.getString(1),
        r.getString(2),
        r.getString(3),
        r.getString(4),
        r.getString(5),
        {
          val p1 = r.getString(6)
          if(p1 == null) Double.NaN
          else p1.toDouble
        },
        {
          val p2 = r.getString(7)
          if(p2 == null) Double.NaN
          else p2.toDouble
        }
      ) },
      null
    )
  }
}

import hypedex.models.payloads.{HypedexPayload, OneDimensionalPayload}
import hypedex.services.KDTreeBuilder
import hypedex.testUtils.SparkContextHolder
import org.apache.spark.sql.Dataset


case class AirQuality(
                       sensor_id: Any,
                       location: Any,
                       lat: Any,
                       lon: Any,
                       timestamp: Any,
                       P1: Double,
                       P2: Double
                     ) extends HypedexPayload {
  override def getDimensions() = Map("P1" -> this.P1, "P2" -> this.P2)
}


val session = SparkContextHolder.getSession()
val dimensArray = Array("P1", "P2")
val treeBuilder = new KDTreeBuilder[AirQuality](session.sqlContext, dimensArray, "D:\\source\\datasets\\sofia-air-quality-dataset\\kd-tree")

val df = session.read.option("header", "true").csv("D:\\source\\datasets\\sofia-air-quality-dataset\\*sds*.csv")


import session.implicits._

val ds: Dataset[AirQuality] = df.as[AirQuality]
val depth = 2

val root = treeBuilder.buildTree(ds, depth)

package hypedex.testUtils

import hypedex.models.payloads.HypedexPayload
import org.apache.spark.sql.Row

case class ParticleAirQuality(
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

object ParticleAirQuality {
  val mappingFunction = {
    r: Row => ParticleAirQuality(
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
    ) }

  val namedMappingFunction = {
    r: Row => ParticleAirQuality(
      r.getAs[String]("sensor_id"),
      r.getAs[String]("location"),
      r.getAs[String]("lat"),
      r.getAs[String]("lon"),
      r.getAs[String]("timestamp"),
      r.getAs[Double]("P1"),
      r.getAs[Double]("P2")
    )
  }
}
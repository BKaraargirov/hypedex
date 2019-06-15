package hypedex.testUtils

import scala.io.Source

object DataLoader {
  /**
    * Get the points data from the resource csv file.
    * @return List of points
    */
  def getPoints(): List[Point] =
    Source.fromResource("pointdata.csv")
    .getLines()
    .toList
    .map(line => {
      if(line.contains("x"))
        return null
      else {
        val pointData: Array[String] = line.split(",")
        Point(pointData(0).toDouble, pointData(1).toDouble, pointData(2).toDouble)
      }
    }).filter(_ != null)
}

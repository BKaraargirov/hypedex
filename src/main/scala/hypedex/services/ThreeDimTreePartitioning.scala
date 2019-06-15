package com.bksilver.codearmory.spark

import java.util.UUID

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SQLContext}


// KD Tree implementation for 3D objects
object ThreeDimTreePartitioning {
  // TODO: Cache the data so each partition does not do everything again and again
  /**
    *
    * @param data
    * @param depth Root is 0
    * @return
    */
  def builtTree(data: Dataset[Point], depth: Int): BaseNode = {
    def loop(data: Dataset[Point], currentDepth: Int): BaseNode = {
      if (currentDepth > depth) {
        PartitionNode(UUID.randomUUID().toString, data)
      }
      else {
        val dimName = getTargetDimension(currentDepth)
        val (left, right, splitPoint) = split(data, dimName)

        DirectionNode(
          dimension = dimName,
          value = splitPoint,
          left = loop(left, currentDepth + 1),
          right = loop(right, currentDepth + 1)
        )
      }
    }

    loop(data, 0)
  }

  /**
    * Split a data set into two on the median
    *
    * @param data          to be split
    * @param dimensionName of the dimension that will be used to split the data on
    * @return
    */
  def split(data: Dataset[Point], dimensionName: String): (Dataset[Point], Dataset[Point], Double) = {
    val splitPoint = data.stat.approxQuantile(dimensionName, Array(0.5), 0).head

    val left = data.filter(p => {
      dimensionName match {
        case "x" => p.x < splitPoint
        case "y" => p.y < splitPoint
        case "z" => p.z < splitPoint
      }
    })

    val right = data.filter(p => {
      dimensionName match {
        case "x" => p.x >= splitPoint
        case "y" => p.y >= splitPoint
        case "z" => p.z >= splitPoint
      }
    })

    (left, right, splitPoint)
  }

  def getTargetDimension(depth: Int): String = depth % 3 match {
    case 0 => "x"
    case 1 => "y"
    case 2 => "z"
  }

  // Override with specific implementation
  def getData(sparkContext: SQLContext): Dataset[Point] = {
    import sparkContext.implicits._

    sparkContext.createDataset(Array(Point(1, 2, 3), Point(5, 10, 15), Point(14.2, 17.5, 100)))
  }
}

trait BaseNode {
  def getLeft(): Option[BaseNode] = None

  def getRight(): Option[BaseNode] = None

  def getData(): Option[Dataset[Point]] = None

  def getSplitPoint(): Option[Double] = None
}

case class DirectionNode(
                          dimension: String,
                          value: Double,
                          left: BaseNode,
                          right: BaseNode,
                        ) extends BaseNode {
  override def getLeft(): Option[BaseNode] = Option(left)

  override def getRight(): Option[BaseNode] = Option(right)

  override def getSplitPoint(): Option[Double] = Option(value)
}

case class PartitionNode(
                          id: String,
                          partition: Dataset[Point]
                        ) extends BaseNode {
  override def getData(): Option[Dataset[Point]] = Option(partition)
}

case class Point(
                  x: Double,
                  y: Double,
                  z: Double
                )

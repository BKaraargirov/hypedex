package com.bksilver.codearmory.spark

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ThreeDimTreePartitioningTests extends FlatSpec with Matchers{
  val conf: SparkConf = new SparkConf().setAppName("ThreeDimTest").setMaster("local")
  val sparkContext = new SparkContext(conf)
  val spark = SparkSession.builder().config(conf).getOrCreate()
  val path = "D:\\Source\\CodeArmory\\src\\test\\resources\\pointdata.csv"

  import spark.implicits._
  val raw = spark.read.option("header", "true").csv(path)
  val data = raw.map(r => Point(r.getAs[String]("x").toDouble,
      r.getAs[String]("y").toDouble,
      r.getAs[String]("z").toDouble))



  ThreeDimTreePartitioning.getTargetDimension(0) shouldEqual "x"

  ThreeDimTreePartitioning.getTargetDimension(1) shouldEqual "y"

  ThreeDimTreePartitioning.getTargetDimension(2) shouldEqual "z"

  ThreeDimTreePartitioning.getTargetDimension(3) shouldEqual "x"


  "Split on x" should "be working" in {
    val (left, right, splitPoint) = ThreeDimTreePartitioning.split(data, "x")

    left.count() shouldEqual 499
    right.count() shouldEqual 501
    splitPoint shouldEqual 5.08
  }

  "Build tree" should "be working" in {
    val root = ThreeDimTreePartitioning.builtTree(data, 2)

    val p1 = root.getLeft().get.getLeft().get.getLeft().get
    val p2 = root.getLeft().get.getLeft().get.getRight().get

    val x1 = root.getSplitPoint().get
    val y1 = root.getLeft().get.getSplitPoint().get
    val z1 = root.getLeft().get.getLeft().get.getSplitPoint().get


    p1.isInstanceOf[PartitionNode] shouldEqual true
    p2.isInstanceOf[PartitionNode] shouldEqual true


    p1.getData().get.filter(p => p.x < x1 && p.y < y1 && p.z < z1).count == p1.getData().get.count() shouldEqual true
    p2.getData().get.filter(p => p.x < x1 && p.y < y1 && p.z >= z1).count == p2.getData().get.count() shouldEqual true
  }
}

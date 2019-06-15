package hypedex.testUtils

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object SparkContextHolder {
  val conf: SparkConf = new SparkConf().setAppName("HypedexTests").setMaster("local")
  val sparkContext = new SparkContext(conf)

  def getSession(): SparkSession = SparkSession.builder().config(conf).getOrCreate()
}

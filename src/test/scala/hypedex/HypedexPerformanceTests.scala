package hypedex

import hypedex.testUtils.SparkContextHolder
import org.scalatest.refspec.RefSpec
import org.scalatest.{FlatSpec, Matchers}

class HypedexPerformanceTests extends RefSpec with Matchers {
  val session = SparkContextHolder.getSession()

  object `Basic requests` {
    def `test speed of normal request` {
      val df = session.read.option("header", "true").csv("D:\\source\\datasets\\sofia-air-quality-dataset\\*sds*.csv")

      val result = session.sql("SELECT * FROM airQuality WHERE P1 > 20")

      println(result.count())
    }
  }
} //start: 19/11/23 20:23:08 //Total 4m 6s 977ms. Count: 35707473

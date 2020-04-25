package hypedex

import hypedex.models.Metadata
import hypedex.queryAnalyzer.IdExtractor
import hypedex.services.{KDTreeBuilder, QueryAnalysisService, SqlParser, SyntaxTreeFactory}
import hypedex.storage.{BasicMetadataRepository, ParquetPartitionStore}
import hypedex.testUtils.{ParticleAirQuality, SparkContextHolder}
import org.scalatest.refspec.RefSpec
import org.scalatest.{FlatSpec, Ignore, Matchers}

class HypedexPerformanceTests extends FlatSpec with Matchers {
  val session = SparkContextHolder.getSession()
  val originalDataset = "D:\\source\\datasets\\sofia-air-quality-dataset\\*sds*.csv"
  val bigParquetDataset = "D:\\source\\datasets\\air-tests\\all-parquet"
  val baseDir = "D:\\source\\datasets\\air-tests\\kd-tree"
  val metadataDir = "D:\\source\\datasets\\air-tests\\metadata-two"
  val metadataId = "1a5d2fa5-d516-42f9-b874-5b7066e606e0"
  val metadataStore = new BasicMetadataRepository[Metadata](metadataDir)
  val partitionRepository = new ParquetPartitionStore[ParticleAirQuality](session)
  val kdTreeBuilder = new KDTreeBuilder[ParticleAirQuality](session.sqlContext, Array("P1", "P2")) //TODO: do not ask for dimensions here
  val dms = new DataCommandService[ParticleAirQuality](session, partitionRepository,
                                                       metadataStore, kdTreeBuilder, ParticleAirQuality.mappingFunction)
  val queryAnalysisService = new QueryAnalysisService[ParticleAirQuality](new SyntaxTreeFactory,
                                                                          new SqlParser(), new IdExtractor())


  "create big parquet" should "work" in {
    import session.sqlContext.implicits._

    val df = session.read.option("header", "true")
      .csv(originalDataset)
      .map(ParticleAirQuality.mappingFunction)

    df.write.parquet(bigParquetDataset)
  }

  "Q1 on csv files" should "run" in {
    import session.sqlContext.implicits._

    val df = session.read
      .option("header", "true")
      .option("inferSchema", true)
      .csv(originalDataset)

    df
      .withColumn("P1", $"P1" cast "Double" as "P1")
      .withColumn("P2", $"P2" cast "Double" as "P2")
      .createTempView("airQuality")

    val result = session
      .sql("SELECT * FROM airQuality WHERE P1 > 29.27 AND P2 < 11.5")
      .map(ParticleAirQuality.mappingFunction)

    println(result.count())
  }

  "Q2 on csv files" should "run" in {
    import session.sqlContext.implicits._

    val df = session.read
      .option("header", "true")
      .option("inferSchema", true)
      .csv(originalDataset)

    df.createTempView("airQuality")

    val result = session.sql("SELECT * FROM airQuality WHERE P1 > 20")
      .map(ParticleAirQuality.mappingFunction)

    println(result.count())
  }

  "Q3 on csv files" should "run" in {
    val df = session.read
      .option("header", "true")
      .option("inferSchema", true)
      .csv(originalDataset)

    df.createTempView("airQuality")

    val result = session.sql("SELECT *, MAX(P2), MIN(P1) FROM airQuality")

    result.collect().foreach(println)
  }



  "Q1 on pure parquet files" should "run" in {
    import session.sqlContext.implicits._

    val df = session.read.option("header", "true").parquet(bigParquetDataset)

    df.createTempView("airQualityP")

    val result = session
      .sql("SELECT * FROM airQualityP WHERE P1 > 29.27 AND P2 < 11.5")
      .map(ParticleAirQuality.namedMappingFunction)

    println(result.count())
  }

  "Q2 on pure parquet files" should "run" in {
    import session.sqlContext.implicits._

    val df = session.read.option("header", "true").parquet(bigParquetDataset)

    df.createTempView("airQualityP")

    val result = session
      .sql("SELECT * FROM airQualityP WHERE P1 > 20")
      .map(ParticleAirQuality.namedMappingFunction)

    println(result.count())
  }

  "Q3 on pure parquet files" should "run" in {
    import session.sqlContext.implicits._

    val df = session.read.option("header", "true").parquet(bigParquetDataset)

    df.createTempView("airQualityP")

    val result = session
      .sql("SELECT *, MAX(P2), MIN(P1) FROM airQualityP")
      .map(ParticleAirQuality.namedMappingFunction)

    println(result.count())
  }


 "test speed of normal request" should "be slower" in {
    val df = session.read.option("header", "true").csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/*2019*sds*.csv")

   df.createTempView("airQuality")

    val result = session.sql("SELECT * FROM airQuality WHERE P1 > 20 AND P2 < 1")

   result.repartition(1).write.csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/no-tree.csv")
   println(result.count())
  }
//
//  "test speed of kd tree one" should "be faster" in {
//    val query = "SELECT * FROM airQualityKD WHERE P1 > 29.27 AND P2 < 11.5"
//
//    import session.sqlContext.implicits._
//
//    val metadata = metadataStore.getMetadataById(metadataId)
//    val predicates = queryAnalysisService.getPartitionPredicate(query)
//    val nodes = queryAnalysisService.findSubset(metadata.treeRoot, predicates)
//
//    val dataset = dms.loadParquets(nodes, baseDir, ParticleAirQuality.namedMappingFunction)
//
//    dataset.createTempView("airQualityKD")
//
//    val result = session.sql(query)
//
//    println(result.count())
//  }
//
//  "test speed of kd tree one without mapping" should "be faster" in {
//    val query = "SELECT * FROM airQualityKD WHERE P1 > 29.27 AND P2 < 11.5"
//    val tempView = "airQualityKD"
//    import session.sqlContext.implicits._
//
//    val metadata = metadataStore.getMetadataById(metadataId)
//    val predicates = queryAnalysisService.getPartitionPredicate(query)
//    val nodes = queryAnalysisService.findSubset(metadata.treeRoot, predicates)
//
//    val dataframe = dms.loadParquets(nodes, baseDir, query, tempView, ParticleAirQuality.namedMappingFunction)
//
//    println(dataframe.count())
//  }
//
//  "test speed of normal request on parquet file 2" should "be slower" in {
//    import session.sqlContext.implicits._
//
//    val df = session.read.option("header", "true").parquet(bigParquetDataset)
//
//    df.createTempView("airQualityP")
//
//    val result = session.sql("SELECT * FROM airQualityP WHERE P1 > 29.27 AND P2 < 11.5").map(ParticleAirQuality.namedMappingFunction)
//
//    println(result.count())
//  }
} //No tree: Total 40s 858ms up to 1m 40s(with repartition for save)
 // With Tree: Total 13s 170ms up to 23s (with repartition for save)result 24

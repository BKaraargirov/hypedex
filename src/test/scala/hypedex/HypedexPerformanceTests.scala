package hypedex

import hypedex.models.Metadata
import hypedex.queryAnalyzer.IdExtractor
import hypedex.services.{AirQuality, DataManipulationService, KDTreeBuilder, QueryAnalysisService, SqlParser, SyntaxTreeFactory}
import hypedex.storage.{BasicMetadataStore, ParquetPartitionStore}
import hypedex.testUtils.SparkContextHolder
import org.scalatest.refspec.RefSpec
import org.scalatest.{FlatSpec, Matchers}

class HypedexPerformanceTests extends FlatSpec with Matchers {
  val session = SparkContextHolder.getSession()
  val baseDir = "/Users/silver/Documents/nbu/sofia-air-quality-dataset/kd-tree"
  val metadataDir = "/Users/silver/Documents/nbu/sofia-air-quality-dataset/metadata"
  val metadataId = "9b9fb525-b07c-41a4-9117-bc4303dc5cb7"
  val metadataStore = new BasicMetadataStore[Metadata](metadataDir)
  val partitionRepository = new ParquetPartitionStore[AirQuality](session)
  val kdTreeBuilder = new KDTreeBuilder[AirQuality](session.sqlContext, Array("P1", "P2")) //TODO: do not ask for dimensions here
  val dms = new DataManipulationService[AirQuality](session, partitionRepository,
    metadataStore, kdTreeBuilder, AirQuality.mappingFunction)
  val queryAnalysisService = new QueryAnalysisService[AirQuality](new SyntaxTreeFactory,
    new SqlParser(), new IdExtractor())

  "create big parquet" should "work" in {
    import session.sqlContext.implicits._

    val df = session.read.option("header", "true")
      .csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/*2019*sds*.csv")
      .map(AirQuality.mappingFunction)

    df.distinct().write.parquet("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/all-parquet")
  }

  "test speed of normal request on parquet file" should "be slower" in {
    val df = session.read.option("header", "true").parquet("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/all-parquet")

    df.createTempView("airQualityP")

    val result = session.sql("SELECT * FROM airQualityP WHERE P1 > 20 AND P2 < 1")

    result.repartition(1).write.csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/no-tree-parquet.csv")
    println(result.count())
  }


 "test speed of normal request" should "be slower" in {
    val df = session.read.option("header", "true").csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/*2019*sds*.csv")

   df.createTempView("airQuality")

    val result = session.sql("SELECT * FROM airQuality WHERE P1 > 20 AND P2 < 1")

   result.repartition(1).write.csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/no-tree.csv")
   println(result.count())
  }

  "test speed of kd tree one" should "be faster" in {
    val query = "SELECT * FROM airQualityKD WHERE P1>20 AND P2<1"

    import session.sqlContext.implicits._

    val metadata = metadataStore.getMetadataById(metadataId)
    val predicates = queryAnalysisService.getPartitionPredicate(query)
    val nodes = queryAnalysisService.findSubset(metadata.treeRoot, predicates)

    val dataset = dms.loadParquets(nodes, baseDir, AirQuality.namedMappingFunction)

    dataset.createTempView("airQualityKD")

    val result = session.sql(query)

    result.distinct().repartition(1).write.csv("/Users/silver/Documents/nbu/sofia-air-quality-dataset/results/with-tree-unique.csv")
    println(result.count())
  }
} //No tree: Total 40s 858ms up to 1m 40s(with repartition for save)
 // With Tree: Total 13s 170ms up to 23s (with repartition for save)result 24

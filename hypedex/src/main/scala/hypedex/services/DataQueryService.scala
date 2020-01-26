package hypedex.services

import hypedex.models.{Metadata, PartitionNode}
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.PartitionNode
import hypedex.storage.{PartitionStore, TMetadataStore}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Row, SparkSession}

class DataQueryService [T <: HypedexPayload](
  session: SparkSession,
  partitionRepository: PartitionStore[T],
  metadataStore: TMetadataStore[Metadata],
  queryAnalysisService: QueryAnalysisService[T]
) {

  //TODO: remove table name
    def executeQuery(query: String, metadataId: String, dir: String, tableName: String, mapper: Row => T): Dataset[T] = {
      import session.sqlContext.implicits._

      val metadata = metadataStore.getMetadataById(metadataId)
      val predicates = queryAnalysisService.getPartitionPredicate(query)
      val nodes = queryAnalysisService.findSubset(metadata.treeRoot, predicates)
      loadParquets(nodes, dir, query, tableName, mapper)
    }

  def loadParquets(targetNodes: List[PartitionNode[T]], baseDir: String,
                   sql: String, tableName: String, mapper: Row => T )(implicit enc: Encoder[T]): Dataset[T]  = {
    val df = this.partitionRepository.load(targetNodes, baseDir)

    df.createTempView(tableName)

    session.sql(sql).map(mapper)
  }


  def loadParquets(targetNodes: List[PartitionNode[T]], baseDir: String, sql: String, tableName: String ): DataFrame  = {
    val df = this.partitionRepository.load(targetNodes, baseDir)

    df.createTempView(tableName)

    session.sql(sql)
  }

}

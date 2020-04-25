package hypedex

import hypedex.models.Metadata
import hypedex.models.payloads.HypedexPayload
import hypedex.partitionConstructor.PartitionNode
import hypedex.services.QueryAnalysisService
import hypedex.storage.{PartitionStore, MetadataRepository}
import org.apache.spark.sql._

class DataQueryService [T <: HypedexPayload](
  session: SparkSession,
  partitionRepository: PartitionStore[T],
  metadataStore: MetadataRepository[Metadata],
  queryAnalysisService: QueryAnalysisService[T]
) {

  //TODO: remove table name
    def executeQuery(query: String, metadataId: String, dir: String, tableName: String, mapper: Row => T)
                    (implicit  enc: Encoder[T]): Dataset[T] = {
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

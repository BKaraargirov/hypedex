package hypedex.storage

import hypedex.models.Metadata
import hypedex.models.payloads.HypedexPayload

/**
  * Responsible for storing and retrieving the metadata
  * @tparam T Metadata or its subclass
  */
trait MetadataRepository[R <: HypedexPayload, T <: Metadata[R]] {
  def save(newMetadata: T): String
  def getMetadataById(id: String): T
  def deleteMetadataById(id: String): Boolean
}

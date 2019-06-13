package hypedex.storage

import hypedex.models.Metadata

/**
  * Responsible for storing and retrieving the metadata
  * @tparam T Metadata or its subclass
  */
trait TMetadataStore[T <: Metadata[Any]] {
  def saveMetadata(newMetadata: T): String
  def getMetadataById(id: String): T
  def deleteMetadataById(id: String): Boolean
}

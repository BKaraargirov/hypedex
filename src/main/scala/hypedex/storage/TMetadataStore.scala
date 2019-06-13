package hypedex.storage

import hypedex.models.Metadata

trait TMetadataStore[T <: Metadata[Any]] {
  def saveMetadata(newMetadata: T): String
}

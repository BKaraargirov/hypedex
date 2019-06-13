package hypedex.storage

import hypedex.models.Metadata

class BasicMetadataStore[T <: Metadata[Any]](val storageLocation: String) extends TMetadataStore[T] {
  /**
    * Save the metadata into the file system
    * @param newMetadata to be stored
    * @return The storage location of the metadata file
    */
  def saveMetadata(newMetadata: T): String = ???

}

object BasicMetadataStore {
  def apply[T <: Metadata[Any]](storageLocation: String): BasicMetadataStore[T] =
    new BasicMetadataStore(storageLocation)
}

package hypedex.storage

import java.io.{FileOutputStream, ObjectOutputStream}

import hypedex.models.Metadata

class BasicMetadataStore[T <: Metadata[Any]](val storageLocation: String) extends TMetadataStore[T] {
  val fileExtension = ".hype"

  /**
    * Save the metadata into the file system
    * @param newMetadata to be stored
    * @return The storage location of the metadata file
    */
  // TODO: Use Either
  def saveMetadata(newMetadata: T): String = {
    val pathToFile = createFilePath(newMetadata.id)
    val fileStream = new FileOutputStream(pathToFile)
    val objectStream = new ObjectOutputStream(fileStream)

    objectStream.writeObject(newMetadata)

    pathToFile
  }


  def createFilePath(metadataId: String): String =
    // TODO: Check if storage location ends with /
    storageLocation + "/" + metadataId + fileExtension
}

object BasicMetadataStore {
  def apply[T <: Metadata[Any]](storageLocation: String): BasicMetadataStore[T] =
    new BasicMetadataStore(storageLocation)
}

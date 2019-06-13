package hypedex.storage

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import hypedex.models.Metadata

/**
  * Responsible for storing and retrieving the metadata from the local HDD
  * @param storageLocation where the metadatas will be placed
  * @tparam T Metadata or its subclass
  */
class BasicMetadataStore[T <: Metadata[Any]](val storageLocation: String) extends TMetadataStore[T] {
  val fileExtension = ".hype"
  //TODO: Create a folder where all metadatas will be stored

  /**
    * Save the metadata into the file system
    * @param newMetadata to be stored
    * @return The storage location of the metadata file
    */
  // TODO: Use Either
  def saveMetadata(newMetadata: T): String = {
    val pathToFile = createPathToFile(newMetadata.id)
    val fileStream = new FileOutputStream(pathToFile)
    val objectStream = new ObjectOutputStream(fileStream)

    objectStream.writeObject(newMetadata)

    pathToFile
  }

  /**
    * Get a metadata by its id from the file system
    * @param metadataId used to locate the file
    * @return the metadata.
    */
  def getMetadataById(metadataId: String): T = {
    val pathToFile = createPathToFile(metadataId)
    val fileReader = new FileInputStream(pathToFile)
    val objectStream = new ObjectInputStream(fileReader)

    objectStream.readObject().asInstanceOf[T]
  }

  /**
    * Create the file path using the predefined path, the metadata id and a predefined file extension.
    * @param metadataId used as fileName
    * @return The full path to the file with its extension and all.
    */
  def createPathToFile(metadataId: String): String = {
    val finalDelimeter = if(storageLocation.endsWith("/")) "" else "/"

    storageLocation + finalDelimeter + metadataId + fileExtension
  }
}

object BasicMetadataStore {
  def apply[T <: Metadata[Any]](storageLocation: String): BasicMetadataStore[T] =
    new BasicMetadataStore(storageLocation)
}

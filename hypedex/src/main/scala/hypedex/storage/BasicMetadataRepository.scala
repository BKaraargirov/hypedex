package hypedex.storage

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.file.{Files, Paths}

import hypedex.models.Metadata
import hypedex.models.payloads.HypedexPayload
import org.apache.commons.io.FileUtils

/**
  * Responsible for storing and retrieving the metadata from the local HDD
  * @param storageLocation where the metadatas will be placed
  * @tparam T Metadata or its subclass
  */
class BasicMetadataRepository[T <: HypedexPayload, M <: Metadata[T]](val storageLocation: String) extends MetadataRepository[T, M] {
  val fileExtension = ".hype"
  //TODO: Create a folder where all metadatas will be stored

  /**
    * Save the metadata into the file system
    * @param newMetadata to be stored
    * @return The storage location of the metadata file
    */
  // TODO: Use Either
  override def save(newMetadata: M): String = {
    val pathToFile = createPathToFile(newMetadata.id)
    val fileStream = new FileOutputStream(pathToFile)
    val objectStream = new ObjectOutputStream(fileStream)

    try{
      objectStream.writeObject(newMetadata)
    } finally {
      objectStream.close()
      fileStream.close()
    }

    pathToFile
  }

  /**
    * Get a metadata by its id from the file system
    * @param metadataId used to locate the file
    * @return the metadata.
    */
  override def getMetadataById(metadataId: String): M = {
    val pathToFile = createPathToFile(metadataId)
    val fileReader = new FileInputStream(pathToFile)
    val objectStream = new ObjectInputStream(fileReader)

    try {
      objectStream.readObject().asInstanceOf[M]
    } finally {
      objectStream.close()
      fileReader.close()
    }
  }

  /**
    * Delete a given metadata from the file system.
    * @param metadataId used to locate the file
    * @return True if it was deleted
    */
  override def deleteMetadataById(metadataId: String): Boolean = {
    val pathToFile = createPathToFile(metadataId)

    val file = Paths.get(pathToFile).toFile

    file.delete()
    file.exists()
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

object BasicMetadataRepository {
  def apply[T <: HypedexPayload, M <: Metadata[T]](storageLocation: String): BasicMetadataRepository[T, M] =
    new BasicMetadataRepository(storageLocation)
}

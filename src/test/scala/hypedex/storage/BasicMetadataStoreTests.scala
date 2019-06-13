package hypedex.storage

import hypedex.models.{KDNode, Metadata}
import org.scalatest.{FlatSpec, Matchers}
import java.nio.file.{Paths, Files}

class BasicMetadataStoreTests extends FlatSpec with Matchers {
  val location = "./"
  val metadataStore: BasicMetadataStore[Metadata[Double]] = BasicMetadataStore(location)

  "Files" should "have been created" in {
    val newMetadata = Metadata[Double](
      id = "TestNode",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode[Double](
        dimensionName = "bla",
        value = 5.0,
        left = Option(KDNode[Double]("t1", 4.0, None, None)),
        right = Option(KDNode[Double]("t1",3.0, None, None)),
      )
    )

    val storageLocation: String = metadataStore.saveMetadata(newMetadata)

    Files.exists(Paths.get(storageLocation)) should equal(true)
    Files.delete(Paths.get(storageLocation))
  }

  "File" should "be readable" in {
    val newMetadata = Metadata[Double](
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode[Double](
        dimensionName = "bla",
        value = 5.0,
        left = Option(KDNode[Double]("t1", 4.0, None, None)),
        right = Option(KDNode[Double]("t1",3.0, None, None)),
      )
    )

    val storageLocation: String = metadataStore.saveMetadata(newMetadata)

    val retrievedMetadata: Metadata[Double] = metadataStore.getMetadataById(newMetadata.id)

    retrievedMetadata.id should equal(newMetadata.id)
    retrievedMetadata.distanceFunction.apply(1,2) should equal(3)
    retrievedMetadata.treeRoot.dimensionName should equal(newMetadata.treeRoot.dimensionName)
    retrievedMetadata.treeRoot.left.isDefined should equal(true)
    retrievedMetadata.treeRoot.left.get.left.isEmpty should equal(true)

    Files.delete(Paths.get(storageLocation))
  }

  "File" should "be deleted properly" in {
    val newMetadata = Metadata[Double](
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode[Double](
        dimensionName = "bla",
        value = 5.0,
        left = Option(KDNode[Double]("t1", 4.0, None, None)),
        right = Option(KDNode[Double]("t1",3.0, None, None)),
      )
    )

    val storageLocation: String = metadataStore.saveMetadata(newMetadata)

    Files.exists(Paths.get(storageLocation)) should equal(true)

    val isDeleted = metadataStore.deleteMetadataById(newMetadata.id)

    Files.exists(Paths.get(storageLocation)) should equal(false)
  }

  "File location with trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp/"
    val metadataName = "test"
    val testStore: BasicMetadataStore[Metadata[Any]] = BasicMetadataStore(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + metadataName + testStore.fileExtension

    actual should equal(expected)
  }

  "File location without trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp"
    val metadataName = "test"
    val testStore: BasicMetadataStore[Metadata[Any]] = BasicMetadataStore(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + "/" + metadataName + testStore.fileExtension

    actual should equal(expected)
  }
}

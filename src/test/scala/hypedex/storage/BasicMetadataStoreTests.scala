package hypedex.storage

import hypedex.models.{EmptyNode, KDNode, Metadata}
import org.scalatest.{FlatSpec, Matchers}
import java.nio.file.{Files, Paths}

class BasicMetadataStoreTests extends FlatSpec with Matchers {
  val location = "/Users/silver/source/Hypedex/testResults"
  val metadataStore: BasicMetadataStore[Metadata] = BasicMetadataStore(location)


  "Files" should "have been created" in {
    val newMetadata = Metadata(
      id = "TestNode",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = KDNode("t1",3.0, EmptyNode(), EmptyNode()),
      )
    )

    val storageLocation: String = metadataStore.saveMetadata(newMetadata)

    Files.exists(Paths.get(storageLocation)) should equal(true)
    Files.delete(Paths.get(storageLocation))
  }

  "File" should "be readable" in {
    val newMetadata = Metadata(
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = KDNode("t1", 3.0, EmptyNode(), EmptyNode()),
      )
    )

    val storageLocation: String = metadataStore.saveMetadata(newMetadata)

    val retrievedMetadata: Metadata = metadataStore.getMetadataById(newMetadata.id)

    retrievedMetadata.id should equal(newMetadata.id)
    retrievedMetadata.distanceFunction.apply(1,2) should equal(3)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].dimensionName should equal(newMetadata.treeRoot.asInstanceOf[KDNode].dimensionName)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].medianValue should equal(5.0)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].left.isInstanceOf[KDNode] should equal(true)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].left.asInstanceOf[KDNode]
      .left.isInstanceOf[EmptyNode] should equal(true)

    Files.delete(Paths.get(storageLocation))
  }

  "File" should "be deleted properly" in {

    val newMetadata = Metadata(
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = KDNode("t1", 3.0, EmptyNode(), EmptyNode()),
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
    val testStore: BasicMetadataStore[Metadata] = BasicMetadataStore(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + metadataName + testStore.fileExtension

    actual should equal(expected)
  }

  "File location without trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp"
    val metadataName = "test"
    val testStore: BasicMetadataStore[Metadata] = BasicMetadataStore(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + "/" + metadataName + testStore.fileExtension

    actual should equal(expected)
  }
}

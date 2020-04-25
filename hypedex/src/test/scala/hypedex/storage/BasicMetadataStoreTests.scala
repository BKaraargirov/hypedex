package hypedex.storage

import java.io.File

import org.scalatest.{FlatSpec, Matchers}
import java.nio.file.{Files, Paths}

import hypedex.models.Metadata
import hypedex.partitionConstructor
import hypedex.partitionConstructor.{EmptyNode, KDNode}
import org.apache.commons.io.FileUtils


class BasicMetadataStoreTests extends FlatSpec with Matchers {
  val location = System.getProperty("user.dir") + "/testResults" // /Users/silver/source/Hypedex/testResults"
  val metadataStore: BasicMetadataRepository[Metadata] = BasicMetadataRepository(location)


  "Files" should "have been created" in {
    val newMetadata = Metadata(
      id = "TestNode",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = partitionConstructor.KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, EmptyNode(), EmptyNode()),
        ),
      "./test"
    )

    val storageLocation: String = metadataStore.save(newMetadata)

    val file = Paths.get(storageLocation).toFile
    file.exists() shouldEqual true
    file.delete()
    file.exists() shouldEqual false
  }

  "File" should "be readable" in {
    val newMetadata = Metadata(
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, EmptyNode(), EmptyNode()),
        ),
      "./test"
    )

    val storageLocation: String = metadataStore.save(newMetadata)

    val retrievedMetadata: Metadata = metadataStore.getMetadataById(newMetadata.id)

    retrievedMetadata.id should equal(newMetadata.id)
    retrievedMetadata.distanceFunction.apply(1,2) should equal(3)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].dimensionName should equal(newMetadata.treeRoot.asInstanceOf[KDNode].dimensionName)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].medianValue should equal(5.0)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].left.isInstanceOf[KDNode] should equal(true)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode].left.asInstanceOf[KDNode]
      .left.isInstanceOf[EmptyNode] should equal(true)

    val file = Paths.get(storageLocation).toFile
    file.exists() shouldEqual true
    file.delete()
    file.exists() shouldEqual false
  }

  "File" should "be deleted properly" in {

    val newMetadata = Metadata(
      id = "TestNodeRead",
      distanceFunction = (x: Double, y: Double) => {x + y},
      treeRoot = partitionConstructor.KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, EmptyNode(), EmptyNode()),
        ),
      "./test"
    )

    val storageLocation: String = metadataStore.save(newMetadata)

    var file = Paths.get(storageLocation).toFile
    file.exists() shouldEqual true

    val isDeleted = metadataStore.deleteMetadataById(newMetadata.id)

    file.exists() shouldEqual false
  }

  "File location with trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp/"
    val metadataName = "test"
    val testStore: BasicMetadataRepository[Metadata] = BasicMetadataRepository(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + metadataName + testStore.fileExtension

    actual should equal(expected)
  }

  "File location without trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp"
    val metadataName = "test"
    val testStore: BasicMetadataRepository[Metadata] = BasicMetadataRepository(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + "/" + metadataName + testStore.fileExtension

    actual should equal(expected)
  }
}

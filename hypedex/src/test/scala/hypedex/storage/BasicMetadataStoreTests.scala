package hypedex.storage

import java.io.File

import org.scalatest.{FlatSpec, Matchers}
import java.nio.file.{Files, Paths}

import hypedex.models.Metadata
import hypedex.models.payloads.OneDimensionalPayload
import hypedex.partitionConstructor
import hypedex.partitionConstructor.{EmptyNode, KDNode}
import org.apache.commons.io.FileUtils


class BasicMetadataStoreTests extends FlatSpec with Matchers {
  val location = System.getProperty("user.dir") + "/testResults" // /Users/silver/source/Hypedex/testResults"
  val metadataStore: BasicMetadataRepository[OneDimensionalPayload, Metadata[OneDimensionalPayload]] = BasicMetadataRepository(location)


  "Files" should "have been created" in {
    val newMetadata = Metadata[OneDimensionalPayload](
      id = "TestNode",
      distanceFunction = (x: OneDimensionalPayload, y: OneDimensionalPayload) => { x.value + y.value },
      treeRoot = partitionConstructor.KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, OneDimensionalPayload("t1", 4.0), EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, OneDimensionalPayload("t1", 3.0), EmptyNode(), EmptyNode()),
        medianPoint = OneDimensionalPayload("bla", 5.0)
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
      distanceFunction = (x: OneDimensionalPayload, y: OneDimensionalPayload) => {x.value + y.value},
      treeRoot = KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, OneDimensionalPayload("t1", 4.0), EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, OneDimensionalPayload("t1", 3.0), EmptyNode(), EmptyNode()),
        medianPoint = OneDimensionalPayload("bla", 5.0)
        ),
      "./test"
    )

    val storageLocation: String = metadataStore.save(newMetadata)

    val retrievedMetadata: Metadata[OneDimensionalPayload] = metadataStore.getMetadataById(newMetadata.id)

    retrievedMetadata.id should equal(newMetadata.id)
    retrievedMetadata.distanceFunction.apply(OneDimensionalPayload("t1", 1),OneDimensionalPayload("t1",2)) should equal(3)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode[OneDimensionalPayload]].dimensionName should
      equal(newMetadata.treeRoot.asInstanceOf[KDNode[OneDimensionalPayload]].dimensionName)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode[OneDimensionalPayload]].medianValue should equal(5.0)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode[OneDimensionalPayload]].left.isInstanceOf[KDNode[OneDimensionalPayload]] should equal(true)
    retrievedMetadata.treeRoot.asInstanceOf[KDNode[OneDimensionalPayload]].left.asInstanceOf[KDNode[OneDimensionalPayload]]
      .left.isInstanceOf[EmptyNode] should equal(true)

    val file = Paths.get(storageLocation).toFile
    file.exists() shouldEqual true
    file.delete()
    file.exists() shouldEqual false
  }

  "File" should "be deleted properly" in {

    val newMetadata = Metadata[OneDimensionalPayload](
      id = "TestNodeRead",
      distanceFunction = (x: OneDimensionalPayload, y: OneDimensionalPayload) => {x.value + y.value},
      treeRoot = partitionConstructor.KDNode(
        dimensionName = "bla",
        medianValue = 5.0,
        left = partitionConstructor.KDNode("t1", 4.0, OneDimensionalPayload("t1", 4.0) , EmptyNode(), EmptyNode()),
        right = partitionConstructor.KDNode("t1", 3.0, OneDimensionalPayload("t1" ,3.0), EmptyNode(), EmptyNode()),
        medianPoint = OneDimensionalPayload("bla" ,5.0)
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
    val testStore: BasicMetadataRepository[OneDimensionalPayload, Metadata[OneDimensionalPayload]] = BasicMetadataRepository(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + metadataName + testStore.fileExtension

    actual should equal(expected)
  }

  "File location without trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp"
    val metadataName = "test"
    val testStore: BasicMetadataRepository[OneDimensionalPayload, Metadata[OneDimensionalPayload]] = BasicMetadataRepository(baseLocation)

    val actual = testStore.createPathToFile(metadataName)

    val expected = baseLocation + "/" + metadataName + testStore.fileExtension

    actual should equal(expected)
  }
}

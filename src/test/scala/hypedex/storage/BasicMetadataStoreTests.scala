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


    Files.exists(Paths.get(storageLocation))
  }

  "File location with trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp/"
    val metadataName = "test"
    val testStore: BasicMetadataStore[Metadata[Any]] = BasicMetadataStore(baseLocation)

    val actual = testStore.createFilePath(metadataName)

    val expected = baseLocation + metadataName + testStore.fileExtension

    actual should equal(expected)
  }

  "File location without trailing /" should "be correct" in {
    val baseLocation ="/usr/local/temp"
    val metadataName = "test"
    val testStore: BasicMetadataStore[Metadata[Any]] = BasicMetadataStore(baseLocation)

    val actual = testStore.createFilePath(metadataName)

    val expected = baseLocation + "/" + metadataName + testStore.fileExtension

    actual should equal(expected)
  }
}

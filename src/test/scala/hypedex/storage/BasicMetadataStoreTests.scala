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
}

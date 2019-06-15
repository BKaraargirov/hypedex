package hypedex.models.payloads

trait HypedexPayload {
  /**
    * Get a ordered collection of the dimensions that will be used for the tree building and their values represented
    * as numbers
    * @return a ordered map of dimensions and their numeric representation of the values
    */
  def getDimensions(): Map[String, Double]
}

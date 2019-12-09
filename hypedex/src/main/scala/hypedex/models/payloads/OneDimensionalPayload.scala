package hypedex.models.payloads


case class OneDimensionalPayload(dimensionName: String, value: Double) extends HypedexPayload {
  /**
    * Get a ordered collection of the dimensions that will be used for the tree building and their values represented
    * as numbers
    *
    * @return a ordered map of dimensions and their values
    */
  override def getDimensions(): Map[String, Double] = Map(dimensionName -> value)
}

object OneDimensionalPayload {
  def bindDimension(dimensionName: String): Double => OneDimensionalPayload =
    OneDimensionalPayload(dimensionName, _)
}

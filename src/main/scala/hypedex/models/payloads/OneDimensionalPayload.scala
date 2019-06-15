package hypedex.models.payloads


case class OneDimensionalPayload[T](dimensionName: String, value: T) extends HypedexPayload {
  /**
    * Get a ordered collection of the dimensions that will be used for the tree building and their values represented
    * as numbers
    *
    * @return a ordered map of dimensions and their values
    */
  override def getDimensions(): Map[String, Any] = Map(dimensionName -> value)
}

object OneDimensionalPayload {
  def bindDimension[T](dimensionName: String): T => OneDimensionalPayload[T] =
    OneDimensionalPayload(dimensionName, _)
}

package hypedex.models

case class KDNode[T](
  dimensionName: String,
  value: T,
  left: Option[KDNode[T]],
  right: Option[KDNode[T]]
) {

}

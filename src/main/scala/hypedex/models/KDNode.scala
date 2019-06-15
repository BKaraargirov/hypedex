package hypedex.models

import hypedex.models.payloads.HypedexPayload

/**
  *
  * @param dimensionName of the dimension used to split the data at the current tree level
  * @param medianValue used for the split
  * @param left All values that where less than the median
  * @param right All values that where bigger or equal to the median
  * @tparam NodeValue
  */
case class KDNode[+NodeValue <: HypedexPayload](
  dimensionName: String,
  medianValue: Double,
  left: Option[KDNode[NodeValue]],
  right: Option[KDNode[NodeValue]]
)

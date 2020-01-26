package hypedex.partitionConstructor

import hypedex.models.TreeNode

/**
  *
  * @param dimensionName of the dimension used to split the data at the current tree level
  * @param medianValue used for the split
  * @param left All values that where less than the median
  * @param right All values that where bigger or equal to the median
  */
@SerialVersionUID(951147233710169888L)
case class KDNode(
  dimensionName: String,
  medianValue: Double,
  left: TreeNode,
  right: TreeNode
) extends TreeNode

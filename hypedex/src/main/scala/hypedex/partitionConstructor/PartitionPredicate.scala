package hypedex.partitionConstructor

import hypedex.queryAnalyzer.models.LogicalTreeNode

class PartitionPredicate(
  val mapping: Map[String, LogicalTreeNode]
) extends Serializable {
  def getPredicate(dimensionName: String): LogicalTreeNode = mapping(dimensionName)

  //region Generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[PartitionPredicate]

  override def equals(other: Any): Boolean = other match {
    case that: PartitionPredicate =>
      (that canEqual this) &&
        mapping == that.mapping
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(mapping)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"PartitionPredicate($mapping)"
  //endregion
}
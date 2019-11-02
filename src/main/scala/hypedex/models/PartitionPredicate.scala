package hypedex.models

class PartitionPredicate(
  val mapping: Map[String, DimensionPredicate]
) extends Serializable {
  def getPredicate(dimensionName: String): DimensionPredicate = mapping(dimensionName)

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
package hypedex.models

case class Metadata[+NodeValue] (
 id: String,
 distanceFunction: (Double, Double) => Double,
 treeRoot: KDNode[NodeValue]
 ) {

}

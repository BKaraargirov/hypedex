package hypedex.queryAnalyzer.models

import scala.collection.immutable.SortedSet

case class NumericAxis () {
  private var boundaries = SortedSet[NumericBoundary]()

}

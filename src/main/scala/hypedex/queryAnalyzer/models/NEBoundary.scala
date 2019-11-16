package hypedex.queryAnalyzer.models

/**
  * Represents a boundary that does not exist
  */
case class NEBoundary () extends NumericBoundary(Double.NaN, Double.NaN)

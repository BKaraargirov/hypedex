package hypedex.partitionConstructor

/**
  * The class is used so the stat.approximateQuantile can know where the value is.
  * Spark cannot serialize it when it's an inner class
  *
  * @param x is a holder
  */
case class CalculationWrapper(x: Double)

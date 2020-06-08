import org.apache.log4j.{Level, Logger}

object SparkOptimized {

  def main(args: Array[String]): Unit = {
    val session = SparkSessionSpec.getLocalSession
    Logger.getRootLogger.setLevel(Level.OFF)

    // Converting DF to RDD Vs DF cost
    RDDVsDF.simulateRDDConvertCost(session)

  }
}

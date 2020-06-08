import org.apache.spark.sql.SparkSession

object SparkSessionSpec {
  def getLocalSession: SparkSession =
    SparkSession
      .builder()
      .appName("Spark Optimizer")
      .master("local[1]")
      .config("spark.driver.host", "localhost")
      .getOrCreate()
}

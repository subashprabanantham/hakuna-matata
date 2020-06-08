import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Random

case class FilmCharacter(name: String, desc: String, screenTime: Int)

object RDDVsDF {
  val dataWithSchema: Seq[FilmCharacter] =
    Seq.fill(1000000)(FilmCharacter("mowgli", "This is a Jungle Book character", 100)) ++
      Seq.fill(1000000)(FilmCharacter("baloo", "This is a Jungle Book character", 100)) ++
      Seq.fill(1000000)(FilmCharacter("bagheera", "This is a Jungle Book character", 100)) ++
      Seq.fill(1000000)(FilmCharacter("akela", "This is a Jungle Book character", 100)) ++
      Seq.fill(1000000)(FilmCharacter("kaa", "This is a Jungle Book character", 100))

  private def costSimulation(session: SparkSession, option: String): String = {
    import session.implicits._
    val characterDF: DataFrame =
      session.sparkContext
        .parallelize(dataWithSchema)
        .toDF
        .repartition(2)

    option.toLowerCase match {
      case "rdd" =>
        SparkUtils.time {
          characterDF.rdd
            .filter(_.getString(0).endsWith("a"))
            .map(row => (row.getString(0), 1))
            .reduceByKey(_ + _)
            .collect()
        }
      case "df" =>
        SparkUtils.time {
          characterDF
            .filter(col("name").endsWith("a"))
            .groupBy(col("name"))
            .count()
            .collect()
        }
    }
  }

  def simulateRDDConvertCost(session: SparkSession): Unit = {
    val options: Seq[String] = Random.shuffle(Seq("df", "rdd"))
    options.foreach(option => {
      System.gc()
      println(s"Running groupBy using [$option]: ${costSimulation(session, option)}")
    })
  }
}

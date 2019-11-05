import com.holdenkarau.spark.testing.{
  DatasetGenerator,
  RDDGenerator,
  SharedSparkContext
}
import generators.SampleData
import org.apache.spark.sql.SparkSession
import org.scalacheck.Gen
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class SparkTestBase
    extends FlatSpec
    with Matchers
    with SharedSparkContext
    with ScalaCheckDrivenPropertyChecks {

  var spark: SparkSession = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    spark = SparkSession.builder().config(sc.getConf).getOrCreate()
  }

  "Dataframe generator" should "print row count" in {
    forAll(SampleData.generator(spark.sqlContext),
           minSize(500),
           sizeRange(1000),
           minSuccessful(50)) { df =>
      println(df.count())
    }
  }
  it should "print sample data" in {
    forAll(SampleData.generator(spark.sqlContext), minSize(100)) { df =>
      df.show(10)
    }
  }

  "RDDGenerator" should "generate String one from given values" in {
    forAll(RDDGenerator.genRDD[String](spark.sparkContext)(
             Gen.oneOf(Seq("Y", "N", "A"))),
           minSize(10)) { rdd =>
      rdd.collect() should contain atLeastOneOf ("Y", "N", "A")
    }
  }

  "DatasetGenerator" should "generate String one from given values" in {
    val sc = spark
    import sc.implicits._
    forAll(DatasetGenerator.genDataset[String](spark.sqlContext)(
             Gen.oneOf(Seq("Y", "N", "A"))),
           minSize(10)) { dataset =>
      dataset.collect() should contain atLeastOneOf ("Y", "N", "A")
    }
  }

  override def afterAll(): Unit = super.afterAll()
}

object SparkTestBase {}

package generators

import com.holdenkarau.spark.testing.{
  Column,
  ColumnGenerator,
  DataframeGenerator
}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.scalacheck.Gen

object SampleData {

  private def schema =
    StructType(
      Seq(
        StructField("name", DataTypes.StringType),
        StructField("amount", DataTypes.DoubleType),
        StructField("description", DataTypes.StringType),
        StructField("flag", DataTypes.StringType)
      )
    )

  private def columnGens: Seq[ColumnGenerator] = {
    Seq(
      new Column("name", Gen.alphaNumStr),
      new Column("amount", Gen.choose(0.0, 100000.0)),
      new Column("description", Gen.alphaUpperStr),
      new Column("flag", Gen.oneOf(Seq("Y", "N")))
    )
  }

  def generator(sqlContext: SQLContext): Gen[DataFrame] =
    DataframeGenerator
      .arbitraryDataFrameWithCustomFields(sqlContext, SampleData.schema)(
        columnGens: _*)
      .arbitrary

}

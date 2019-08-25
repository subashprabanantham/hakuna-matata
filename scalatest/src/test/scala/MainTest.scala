import Main._
import MainTest._
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

class MainTest extends BaseScalaSpec {
  "div function" should "return valid result for generated random values" in {
    div(4, 2) shouldBe 2

    // Let's use Gen
    forAll(arbitraryInts, nonZeroInts) { (n: Int, d: Int) =>
      div(n, d) shouldBe n / d
    }

    // Let's use Gen with a condition while consuming
    forAll(arbitraryInts, arbitraryInts) { (n: Int, d: Int) =>
      whenever(d != 0) {
        div(n, d) shouldBe n / d
      }
    }

    // Using choose function
    forAll(Gen.choose(1, 1000), Gen.choose(10, 100)) { (n: Int, d: Int) =>
      div(n, d) shouldBe n / d
    }
  }
  it should "return 1 given same values as numerator and denominator" in {
    var iterations = 0
    Main.div(10, 10) shouldBe 1

    // Let's use Gen
    forAll(nonZeroInts, minSuccessful(50)) { n: Int =>
      iterations += 1
      div(n, n) shouldBe 1
    }

    println(s"Test ran for $iterations iterations")
  }
  it should "throw IllegalArgumentException with denominator 0" in {
    an[IllegalArgumentException] should be thrownBy div(10, 0)

    // Let's use Gen
    forAll(arbitraryInts) { n: Int =>
      an[IllegalArgumentException] should be thrownBy div(n, 0)
    }
  }

  "append function" should "return only one element when added in empty list" in {
    val emptyList = List.empty[Int]

    append(emptyList, 1) should have size 1

    // Let's use Gen
    forAll(arbitraryInts) { n: Int =>
      append(emptyList, n) should have size 1
    }
  }
  it should "append the element at the end of the list" in {
    var iterations     = 0
    val appendingValue = Int.MaxValue

    append(List(1, 2, 3, 4, 4, 6, 3, 2), appendingValue).last shouldBe appendingValue

    // Let's use Gen
    forAll(arbitraryIntList, minSuccessful(100)) { l: List[Int] =>
      iterations += 1
      val appended = append(l, appendingValue)
      appended.last shouldBe appendingValue
    }
    println(s"Test ran for $iterations iterations")
  }
}

object MainTest {
  val arbitraryInts: Gen[Int]          = arbitrary[Int]
  val nonZeroInts: Gen[Int]            = arbitrary[Int] suchThat (_ != 0)
  val arbitraryIntList: Gen[List[Int]] = arbitrary[List[Int]]
}

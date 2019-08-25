import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait BaseScalaSpec extends FlatSpec with Matchers with BeforeAndAfterAll with ScalaCheckDrivenPropertyChecks {

}

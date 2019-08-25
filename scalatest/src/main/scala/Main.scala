object Main {

  def div(n: Int, d: Int): Double = {
    require(d != 0, "Denominator should not be 0")
    n / d
  }

  def append(list: List[Int], postfix: Int): List[Int] = {
    list :+ postfix
  }
}

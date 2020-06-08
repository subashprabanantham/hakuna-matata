object SparkUtils {
  def time[T](fn: => T): String = {
    val startTime = System.nanoTime()
    val f: T      = fn
    val endTime   = System.nanoTime()
    s"Time take: ${(endTime - startTime) / 1000 / 1000} ms"
  }
}

import cats.effect.{ExitCode, IO, IOApp}

object CatsIO extends IOApp {

  def printLine(str: String): IO[Unit] = IO(println(str))
  def readLine: IO[String]             = IO(scala.io.StdIn.readLine)

  override def run(args: List[String]): IO[ExitCode] = {
    val result = for {
      _    <- printLine("IO monad is running ?")
      data <- readLine
      _    <- printLine(s"Answer: $data")
    } yield ()

    for {
      _    <- Option(println("Option is running ?"))
      data <- Option(scala.io.StdIn.readLine)
      _    <- Option(println(s"Answer for Options: $data"))
    } yield ()

    result.unsafeRunSync()

    IO.pure(ExitCode.Success)
  }
}

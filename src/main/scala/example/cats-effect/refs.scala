package example.`cats-effect`

import cats.effect._
//import cats.effect.Concurrent.Ref
import cats.implicits._

object RefUpdateImpure extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    for {
      ref <- Ref[IO].of(0)
      _ <- List(1, 2, 3).parTraverse(task(_, ref))
    } yield ExitCode.Success

  def task(id: Int, ref: Ref[IO, Int]): IO[Unit] =
    ref
      .modify(previous =>
        id -> println(s"$previous->$id")
      ) // optimistic update strategy, retries possible so must be pure
      .replicateA(3)
      .void
}

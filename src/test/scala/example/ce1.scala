package cats.testing

import cats.Id
import cats.effect._
import cats.effect.testing.specs2._

import cats.effect.testkit._
import cats.implicits._
import cats.syntax.all._
import fs2._
// import org.scalatest.freespec.AsyncFreeSpec
// import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeoutException
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util._
import cats.effect.std.Random

import cats.effect.testing.specs2.CatsEffect
import org.specs2.mutable.Specification
import org.specs2.matcher.Matcher

// for some reason, only class works here; object will not be detected by sbt
class ExampleSpec extends Specification with CatsEffect {

  def retry[A](
      ioa: IO[A],
      delay: FiniteDuration,
      max: Int,
      random: Random[IO]
  ): IO[A] =
    if (max <= 1)
      ioa
    else
      ioa handleErrorWith { _ =>
        random.betweenLong(0L, delay.toNanos) flatMap { ns =>
          IO.sleep(ns.nanos) *> retry(ioa, delay * 2, max - 1, random)
        }
      }

  "examples" should {
    "do the things" in IO {
      true must beTrue
    }

    "run a simple IO" in {
      val simple = IO.unit
      TestControl.execute(simple) flatMap { control =>
        for {
          r1 <- control.results
          _ <- IO(r1 must beNone)

          _ <- control.tick

          r2 <- control.results
          //_ <- IO(r2 mustEqual (Outcome.succeeded(r2)))
          _ <- IO(r2 must beSome(beSucceeded(())))
          //_ <- IO(r2 must beSome(kernel.Outcome.Succeeded(())))
        } yield ok
      }
    }

    "retry at least 3 times until success" in {
      case object TestException extends RuntimeException

      var attempts = 0
      val action = IO {
        attempts += 1

        if (attempts != 3)
          throw TestException
        else
          "success!"
      }

      val program = Random.scalaUtilRandom[IO] flatMap { random =>
        retry(action, 1.minute, 5, random)
      }

      TestControl.executeEmbed(program) flatMap { r =>
        IO(r mustEqual ("success!"))
      }
    }
  }

  private def beSucceeded[A](value: A): Matcher[Outcome[Id, Throwable, A]] =
    (_: Outcome[Id, Throwable, A]) == Outcome.succeeded[Id, Throwable, A](value)

}

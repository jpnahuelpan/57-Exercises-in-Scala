//> using lib "org.slf4j:slf4j-simple:2.0.11"
//> using lib "org.http4s::http4s-dsl:0.23.25"
//> using lib "org.http4s::http4s-ember-server:0.23.25"
//> using lib "org.http4s::http4s-circe:0.23.25"
//> using lib "io.circe::circe-generic:0.14.6"
//> using lib "io.circe::circe-literal:0.14.6"

import cats.effect.{IOApp, IO, ExitCode}
import com.comcast.ip4s.{ipv4, port}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder

// work with json
import io.circe.Json
import io.circe.literal.json
import org.http4s.circe.jsonEncoder

import cats.effect.unsafe.IORuntime
implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

// to get local time and format
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/** Creating Your Own Time Service
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Simple web service that returns the current time as
  * JSON data, such as: { "currentTime": "2050-01-24 15:06:26" }.
  *
  * Note:
  *   Execution comand should be:
  *     for service (teminal 1):
  *       scala-cli exercise52.scala
  *     for 'client':
  *       open another terminal an execute (terminal 2):
  *         curl http://localhost:8080/time
  *
  *   slf4j-simple is used to view the service status.
  */


object Exercise52 extends IOApp {

  def currentTimeJson(timeNow: LocalDateTime): Json =
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    json"""{"currentTime": ${timeNow.format(format)}}"""

  val currentTimeService = HttpRoutes.of[IO] {
    case GET -> Root / "time" =>
      Ok(currentTimeJson(LocalDateTime.now))
  }.orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(currentTimeService)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}

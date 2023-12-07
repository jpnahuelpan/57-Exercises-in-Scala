//> using lib "org.http4s::http4s-ember-client:0.23.24"
//> using lib "org.http4s::http4s-circe:0.23.24"
//> using lib "io.circe::circe-generic:0.14.6"
//> using lib "org.slf4j:slf4j-nop:2.0.9"

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.circe.jsonOf
import io.circe.generic.auto.*
// local import
import src.exe_47.{Data, printThePeople}

/** Who’s in Space?
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that pulls in this data and displays the information from
  * this API (http://api.open-notify.org/astros.json) in a tabular format.
  * 
  * Note:
  *   Execution comand should be:
  *     scala-cli exercise47.scala src/exe_47
  *
  *   The only purpose of slf4j-nop is to prevent client log messages from being printed in the prompt.
  */
implicit val runtime: IORuntime = IORuntime.global
@main def exercise47(): Unit =
  val apiUrl = "http://api.open-notify.org/astros.json"
  val request: IO[Data] = EmberClientBuilder
    .default[IO]
    .build
    .use { httpClient =>
      httpClient.expect[Data](apiUrl)(jsonOf[IO, Data])
  }
  val response: Data = request.unsafeRunSync()
  printThePeople(response)

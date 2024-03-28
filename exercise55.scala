//> using lib "org.slf4j:slf4j-simple:2.0.12"
//> using lib "org.http4s::http4s-dsl:0.23.26"
//> using lib "org.http4s::http4s-ember-server:0.23.26"
//> using lib "org.http4s::http4s-circe:0.23.26"
//> using lib "io.circe::circe-literal:0.14.6"
//> using lib "redis.clients:jedis:5.1.2"

import cats.effect.{IOApp, IO, ExitCode}
import cats.effect.unsafe.IORuntime
import com.comcast.ip4s.{ipv4, port}
import org.http4s.server.middleware.CORS
import org.http4s.{HttpRoutes,StaticFile,EntityDecoder}
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.circe.jsonOf
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder

import fs2.io.file.Path
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

// work with json
import io.circe.literal.json

import java.security.MessageDigest

import redis.clients.jedis.JedisPooled

import scala.collection.mutable.ListBuffer
import scala.util.Random

import src.exe_55.{TEXT, TEXT_UPDATED}


/** Text Sharing
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Simple web app that meets the following specifications:
  *   • The user should enter the text into a text area and save
  *     the text.
  *   • The text should be stored in a data store.
  *   • The program should generate a URL that can be used
  *     to retrieve the saved text.
  *   • When a user follows that URL, the text should be dis-
  *     played, along with an invitation to edit the text.
  *   • When a user clicks the Edit button, the text should be
  *     copied and placed in the same interface used to create
  *     new text snippets.
  *
  * Note:
  *   Before execute the program you should init de Redis server in another
  *   terminal with this command:
  *     docker run --name redis -d -p 6379:6379 redis redis-server --save 60 1 --loglevel warning
  *
  *     You can replace docker for podman (if you use podman).
  *
  *   Execution command should be:
  *     scala-cli exercise55.scala src/exe_55
  *
  *
  *   Library and Guides:
  *   - The script utilizes the server client library for Scala (driver) and MongoDB guides.
  *     https://redis.io/docs/get-started/data-store/
  *     https://javadoc.io/doc/redis.clients/jedis/5.1.0/index.html
  *
  */

implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global
implicit val textDecoder: EntityDecoder[IO, TEXT] = jsonOf[IO, TEXT]
implicit val textUpdatedDecoder: EntityDecoder[IO, TEXT_UPDATED] = jsonOf[IO, TEXT_UPDATED]
implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

// Global consts
val INDEX_PATH: String = "src/exe_55/templates/index.html"
val ERROR_404_PATH: String = "src/exe_55/templates/404.html"
val TEXT_PATH: String = "src/exe_55/templates/text.html"

val HOST = ipv4"127.0.0.1" // localhost
val PORT = port"8080"

// Singleton connection.
object RedisConnection {
  lazy val jedis = new JedisPooled("localhost", 6379)
}

object Exercise55 extends IOApp {
  val jedis: JedisPooled = RedisConnection.jedis

  val currentTimeService = HttpRoutes.of[IO] {
    case request @ GET -> Root =>
      StaticFile.fromPath(Path(INDEX_PATH), Some(request))
      .getOrElseF(NotFound())

    case request @ GET -> Root / idText =>
      val text = jedis.get(idText)
      if text != null then
        StaticFile.fromPath(Path(TEXT_PATH), Some(request))
          .getOrElseF(NotFound())
      else
        StaticFile.fromPath(Path(ERROR_404_PATH), Some(request))
          .getOrElseF(NotFound())

    case GET -> Root / "api" / "data" =>
      val keys = jedis.keys("*")
      val keyList: ListBuffer[String] = ListBuffer()
      keys.forEach(key => keyList.append(key))
      Ok(keyList)

    case GET -> Root / "api" / "text" / textId =>
      val text = jedis.get(textId)
      Ok(json"""{"text": ${text}}""")

    case req @ POST -> Root / "api" / "insert" =>
      req.as[TEXT].flatMap(text =>
        val id = idTextGenerator(jedis)
        val status = jedis.set(id, text.text)
        if (status == "OK") {
          IO.delay(json"""{"message": "ok"}""")
        } else {
          IO.delay(json"""{"message": "Don't insert..."}""")
        }
      ).flatMap(msg => Ok(msg))

    case req @ PUT -> Root / "api" / "update" =>
      req.as[TEXT_UPDATED].flatMap(text =>
        val status = jedis.set(text.id, text.text)
        if (status == "OK") {
          IO.delay(json"""{"response": "ok" , "message": "Text updated."}""")
        } else {
          IO.delay(json"""{"response": "Don't updated..."}""")
        }
      ).flatMap(msg => Ok(msg))
  }.orNotFound

  val corsService = CORS.policy.withAllowOriginAll(currentTimeService)

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(HOST)
      .withPort(PORT)
      .withHttpApp(corsService)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}

def randomText: String =
  val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  val rnd = new Random()
  val sb = new StringBuilder()
  for _ <- 1 to 15 do
    val idx = rnd.nextInt(letters.length)
    sb.append(letters.charAt(idx))
  sb.toString()

def generateSHA256(input: String): String =
  try
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(input.getBytes("UTF-8"))
    val hexString = new StringBuilder

    for b <- hashBytes do
      val hex = Integer.toHexString(0xff & b)
      if (hex.length == 1) hexString.append('0')
      hexString.append(hex)

    hexString.toString()
  catch
    case e: Exception =>
      println(e)
      null

def idTextGenerator(jedis: JedisPooled): String =
  var id = generateSHA256(randomText)
  while jedis.get(id) != null do
    id = generateSHA256(randomText)
  id

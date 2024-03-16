//> using lib "org.slf4j:slf4j-nop:2.0.12"
//> using lib "org.mongodb.scala:mongo-scala-driver_2.13:5.0.0"
//> using lib "org.http4s::http4s-dsl:0.23.26"
//> using lib "org.http4s::http4s-ember-server:0.23.26"
//> using lib "org.http4s::http4s-ember-client:0.23.26"
//> using lib "org.http4s::http4s-circe:0.23.26"
//> using lib "io.circe::circe-generic:0.14.6"
//> using lib "io.circe::circe-literal:0.14.6"

import cats.effect.{IOApp, IO, ExitCode}
import cats.effect.unsafe.IORuntime
import com.comcast.ip4s.{ipv4, port}
import org.http4s.server.middleware.CORS
import org.http4s.server.staticcontent._
import org.http4s.{
  HttpRoutes, Uri,
  MediaType, StaticFile,
  Method, Request, EntityDecoder
  }
import org.http4s.implicits.uri
import org.http4s.headers.Location
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.circe.jsonOf
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder

import fs2.io.file.Path
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

// work with json
import io.circe.Json
import io.circe.literal.json
import org.http4s.circe.jsonEncoder

// to get local time and format
import java.util.Date

import org.mongodb.scala.{
  ConnectionString, MongoClient, MongoClientSettings,
  MongoCollection, SingleObservable,
  MongoDatabase
  }
import org.mongodb.scala.result.{InsertOneResult, UpdateResult}
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.{IndexOptions, Indexes}
import com.mongodb.{ServerApi, ServerApiVersion}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.Random
import scala.collection.mutable.ListBuffer

import src.exe_54.{URL, VISIT}

/** URL Shortener
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Simple command-line todo list program that meets the following specifications:
  *   • The program should have a form that accepts the long
  *     URL.
  *   • The program should generate a short local URL like
  *     /abc1234 and store the short URL and the long URL
  *     together in a persistent data store.
  *   • The program should redirect visitors to the long URL
  *     when the short URL is visited.
  *   • The program should track the number of times the short
  *     URL is visited.
  *   • The program should have a statistics page for the short
  *     URL, such as /abc1234/stats . Visiting this URL should
  *     show the short URL, the long URL, and the number of
  *     times the short URL was accessed.
  *
  * Note:
  *   Before execute the program you should init de MongoDB server in another
  *   terminal with this command:
  *     docker run --name mongo -d -p 27017:27017 mongodb/mongodb-community-server:latest
  *
  *     You can replace docker for podman (if you use podman).
  *
  *   Execution command should be:
  *     scala-cli exercise54.scala src/exe_54
  *
  *
  *   Library and Guides:
  *   - The script utilizes the server client library for Scala (driver) and MongoDB guides.
  *     https://mongodb.github.io/mongo-java-driver/4.11/driver-scala/getting-started/quick-start/
  *     https://mongodb.github.io/mongo-java-driver/4.11/driver-scala/
  *
  */

implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global
implicit val personDecoder: EntityDecoder[IO, URL] = jsonOf[IO, URL]
implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]

// Global consts
val MONGO_URL: String = "mongodb://localhost:27017" //default port
val DDBB_NAME: String = "exercise54DB";
val COLLECTION_URL: String = "URLs"
val COLLECTION_VISITS: String = "Visits"
val TIMEOUT: FiniteDuration = 5.seconds
val INDEX_PATH: String = "src/exe_54/templates/index.html"
val ERROR_404_PATH: String = "src/exe_54/templates/404.html"
val STATS_PATH: String = "src/exe_54/templates/stats.html"

val HOST = ipv4"127.0.0.1" // localhost
val PORT = port"8080"

object Exercise54 extends IOApp {
  var createtingConection = true
  while createtingConection do
    try
      val mongoClient: MongoClient = getMongoClient
      createIdx(mongoClient, COLLECTION_URL, "shortURL") // if index exist, don't create problems.
      createIdx(mongoClient, COLLECTION_URL, "longURL")
      mongoClient.close()
      createtingConection = false
    catch
      case e: Exception => println(s"Conection error: ${e}, please run MongoDB in another terminal to fix it.")

  val currentTimeService = HttpRoutes.of[IO] {
    case request @ GET -> Root =>
      StaticFile.fromPath(Path(INDEX_PATH), Some(request))
      .getOrElseF(NotFound())

    case request @ GET -> Root / url =>
      val mongoClient = getMongoClient
      val doc = getOneDoc(mongoClient, COLLECTION_URL, url)
      if doc != null then
        val longURL = doc.get("longURL").get.asString().getValue()
        val redirectUrl = Uri.fromString(longURL).getOrElse(uri"/")
        val docVisit = Document(
          "timestamp" -> new Date(),
          "url" -> doc.get("_id").get
        )
        // here insert a visit record.
        insertOne(mongoClient, COLLECTION_VISITS, docVisit)
        updateVisitsOnUrl(mongoClient, COLLECTION_URL, doc)
        mongoClient.close()
        TemporaryRedirect(Location(redirectUrl))
      else
        StaticFile.fromPath(Path(ERROR_404_PATH), Some(request))
          .getOrElseF(NotFound())

    case request @ GET -> Root / url / "stats" =>
      val mongoClient = getMongoClient
      val doc = getOneDoc(mongoClient, COLLECTION_URL, url)
      mongoClient.close()
      if doc != null then
        StaticFile.fromPath(Path(STATS_PATH), Some(request))
        .getOrElseF(NotFound())
      else
        StaticFile.fromPath(Path(ERROR_404_PATH), Some(request))
          .getOrElseF(NotFound())

    case GET -> Root / "api" / "data" =>
      val mongoClient: MongoClient = getMongoClient
      val allDocs = getDocs(mongoClient, COLLECTION_URL)
      val docs = for doc <- allDocs
        yield doc.toJson()
      mongoClient.close()
      Ok(docs)

    case GET -> Root / "api" / "visits" / url =>
      val mongoClient: MongoClient = getMongoClient
      val doc = getOneDoc(mongoClient, COLLECTION_URL, url)
      val allDocs = getVisitsByUrl(mongoClient, COLLECTION_VISITS, doc)
      val docs = for doc <- allDocs
        yield doc.toJson()
      mongoClient.close()
      Ok(docs)

    case req @ POST -> Root / "api" / "insert" =>
      req.as[URL].flatMap(url =>
        if (urlExists(url.url).unsafeRunSync()) {
          var mongoClient: MongoClient = getMongoClient
          val msg = json"""{"message": ${insertURL(mongoClient, COLLECTION_URL, url)}}"""
          IO.delay(mongoClient.close())
          IO.delay(msg)
        } else {
          IO.delay(json"""{"message": "The URL does not exist, please enter a valid URL."}""")
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

def getMongoClient: MongoClient =
  val serverApi = ServerApi.builder.version(ServerApiVersion.V1).build()
  val settings = MongoClientSettings
    .builder()
    .applyConnectionString(ConnectionString(MONGO_URL))
    .serverApi(serverApi)
    .build()
  MongoClient(settings)

def getCollection(mongoClient: MongoClient, collectionName: String): MongoCollection[Document] =
  val database: MongoDatabase = mongoClient.getDatabase(DDBB_NAME)
  database.getCollection(collectionName)

def createIdx(mongoClient: MongoClient, collectionName: String, key: String): Unit =
  val indexOptions = IndexOptions().unique(true)
  val collection = getCollection(mongoClient, collectionName)
  val index = collection.createIndex(Indexes.ascending(key), indexOptions).head()
  Await.result(index, TIMEOUT)

def getOneDoc(mongoClient: MongoClient, collectionName: String, shortURL: String): Document =
  val collection = getCollection(mongoClient, collectionName)
  val query = collection.find(equal("shortURL", shortURL)).first().head()
  Await.result(query, TIMEOUT)

def insertURL(mongoClient: MongoClient, collectionName: String, url: URL): String =
  var generatingUrl = true
  var randomUrl = s"${generateShortUrl}"
  while generatingUrl do
    if getOneDoc(mongoClient, collectionName, randomUrl) != null then
      randomUrl = s"${generateShortUrl}"
    else
      generatingUrl = false
  val doc = Document(
    "visits" -> 0,
    "shortURL" -> randomUrl,
    "longURL" -> url.url
  )
  insertOne(mongoClient, collectionName, doc)

def insertOne(mongoClient: MongoClient, collectionName: String, doc: Document): String =
  var msg = ""
  val collection = getCollection(mongoClient, collectionName)
  val observable: SingleObservable[InsertOneResult] = collection.insertOne(doc)
  observable.subscribe(
    (e: Throwable) => msg = s"${e.getMessage}",
    () => msg = "ok"
  )
  while msg == "" do
    Thread.sleep(10)
  msg

def getDocs(mongoClient: MongoClient, collectionName: String): ListBuffer[Document] =
  val docs = ListBuffer[Document]()
  var processing = true
  val collection = getCollection(mongoClient, collectionName)
  val query = collection.find()
  query.subscribe(
    (doc: Document) => docs.append(doc),
    (e: Throwable) => throw e,
    () => processing = false
  )
  while processing do
    Thread.sleep(10)
  docs

def getVisitsByUrl(mongoClient: MongoClient, collectionName: String, doc: Document): ListBuffer[Document] =
  val docs = ListBuffer[Document]()
  var processing = true
  val collection = getCollection(mongoClient, collectionName)
  val query = collection.find(equal("url", doc.get("_id").get))
  query.subscribe(
    (doc: Document) => docs.append(doc),
    (e: Throwable) => throw e,
    () => processing = false
  )
  while processing do
    Thread.sleep(10)
  docs

def updateVisitsOnUrl(mongoClient: MongoClient, collectionName: String, doc: Document): Unit =
  var processing = true
  val collection = getCollection(mongoClient, collectionName)
  val update = collection.updateOne(equal("_id", doc.get("_id").get), Document("$inc" -> Document("visits" -> 1))).head()
  Await.result(update, TIMEOUT)


def urlExists(urlString: String): IO[Boolean] =
  EmberClientBuilder
    .default[IO]
    .build
    .use { client =>
      try
        val uri = org.http4s.Uri.unsafeFromString(urlString)
        val request = Request[IO](Method.GET, uri)
        client.status(request).map(_.isSuccess).handleError(_ => false)
      catch
        case e: Exception => IO.delay(false)
    }

def generateShortUrl: String =
  val random = new Random
  val randomNumber = random.nextInt(10000)
  f"$randomNumber%04d"

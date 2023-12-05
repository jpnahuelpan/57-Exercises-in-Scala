package src.exe_50

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.circe.jsonOf
import io.circe.generic.auto.*
import io.circe.{Decoder, HCursor}

import scala.io.StdIn.readLine


implicit val runtime: IORuntime = IORuntime.global

def getMovieData(title: String, key: String): MovieList =
  import java.net.URLEncoder
  val encodedQuery = URLEncoder.encode(title, "UTF-8")
  val apiUrl = s"https://omdbapi.com/?apikey=$key&s=$encodedQuery"
  val request: IO[MovieList] = EmberClientBuilder
    .default[IO]
    .build
    .use { httpClient =>
      httpClient.expect[MovieList](apiUrl)(jsonOf[IO, MovieList])
  }
  request.unsafeRunSync()

def fitText(texto: String, maxWidth: Int): String =
  val words = texto.split("\\s+")
  val lines = new StringBuilder
  var actualLine = new StringBuilder
  for word <- words do
    if (actualLine.length + word.length <= maxWidth) then
      actualLine.append(word).append(" ")
    else
      lines.append(actualLine.toString().trim).append("\n")
      actualLine = new StringBuilder(word + " ")
  lines.append(actualLine.toString().trim)
  lines.toString

def createTextMovie(data: MovieData): String =
  val title = if (data.Title.length < 35) data.Title else fitText(data.Title, 35)
  s"Title: ${title}\n" +
  s"Year: ${data.Year}\n"+
  s"Type: ${data.Type}"

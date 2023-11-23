package src.exe_49

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.circe.jsonOf
import io.circe.generic.auto.*
import io.circe.{Decoder, HCursor}
import scala.io.StdIn.readLine


implicit val runtime: IORuntime = IORuntime.global

def getImgData(topic: String): DataImages =
  val apiUrl = "https://www.flickr.com/services/feeds/photos_public.gne/" +
    s"search?tags=$topic&format=json&nojsoncallback=1"
  val request: IO[DataImages] = EmberClientBuilder
    .default[IO]
    .build
    .use { httpClient =>
      httpClient.expect[DataImages](apiUrl)(jsonOf[IO, DataImages])
  }
  request.unsafeRunSync()

def extractImageDimenssions(text: String, `var`: String): Int =
  val pattern = s"""${`var`}=\"\\d+\"""".r
  val nPattern = """\d+""".r
  nPattern.findFirstMatchIn(pattern.findFirstMatchIn(text).toString) match {
    case Some(value) => value.matched.toInt
    case None => 0
  }

def extractImagesUrls(topic: String): List[ExtractedImgData] =
  val imgData = getImgData(topic).items
  imgData.map(x => ExtractedImgData(
    imgUrl=x.media.m,
    width=extractImageDimenssions(x.description, "width"),
    height=extractImageDimenssions(x.description, "height"),
  ))
import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.circe.jsonOf
import io.circe.generic.auto.*
import cats.effect.unsafe.implicits.global
import scala.io.StdIn.readLine
// local import
import src.exe_48.{WeatherData, key}

/** Grabbing the Weather
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that prompts for a city name and
  * returns the current temperature for the city.
  * 
  * Note:
  *   Execution comand should be:
  *     scala-cli exercise48.scala --dep org.http4s::http4s-ember-client:0.23.24 --dep org.http4s::http4s-circe:0.23.24 \
        --dep io.circe::circe-generic:0.14.6 --dep org.slf4j:slf4j-nop:2.0.9 src/exe_48
  *
  *   The only purpose of slf4j-nop is to prevent client log messages from being printed in the prompt.
  *
  *   Regarding the API Key, it should be requested by registering at http://openweathermap.org.
  *   Then, create a file named 'ApiKey.scala' in the src/exe_48 folder and inside it, add the following:
  *
  *   1| package src.exe_48
  *   2| val key: String = "<your key>"
  *
  */
@main def exercise48(): Unit =
  var getValues = true
  while getValues do
    try
      val city = readLine("Where you are <city,country code>: ")
      val weather = getWeatherData(city, key)
      println(s"$city weather:")
      println(s"${weather.main.temp} degrees Celcius")
      println(s"Pressure of ${weather.main.pressure} hPa")
      println(s"Humidity of ${weather.main.humidity}%")
      getValues = false
    catch
      case e: Exception => println("Perhaps the city is misspelled or there are no records., Try again.\n")

def getWeatherData(city: String, key: String): WeatherData =
  val apiUrl = "https://api.openweathermap.org/data/2.5/" +
    s"weather?q=$city&units=metric&APPID=$key"
  val request: IO[WeatherData] = EmberClientBuilder
    .default[IO]
    .build
    .use { httpClient =>
      httpClient.expect[WeatherData](apiUrl)(jsonOf[IO, WeatherData])
  }
  request.unsafeRunSync()

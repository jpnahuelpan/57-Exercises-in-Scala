package src.exe_48

case class Weather(
  id: Int,
  main: String,
  description: String,
  icon: String
)

case class Sys(
  `type`: Int,
  id: Int,
  country: String,
  sunrise: Int,
  sunset: Int
)

case class Main(
  temp: Float,
  feels_like: Float,
  temp_min: Float,
  temp_max: Float,
  pressure: Int,
  humidity: Int
)

case class Wind(
  speed: Float,
  deg: Int
)

case class WeatherData(
  coord: Map[String, Float],
  weather: List[Weather],
  base: String,
  main: Main,
  visibility: Int,
  wind: Wind,
  clouds: Map[String, Int],
  dt: Int,
  sys: Sys,
  timezone: Int,
  id: Int,
  name: String,
  cod: Int
)

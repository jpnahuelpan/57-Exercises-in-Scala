package src.exe_50

case class MovieData(
  Title: String,
  Year: String,
  imdbID: String,
  Type: String,
  Poster: String
)

case class MovieList(
  Search: List[MovieData],
  totalResults: String,
  Response: String
)

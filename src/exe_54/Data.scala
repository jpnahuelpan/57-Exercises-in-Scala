package src.exe_54

import io.circe.{Decoder, HCursor}

case class URL(
  url: String
)

object URL {
  implicit val personDecoder: Decoder[URL] = new Decoder[URL] {
    override def apply(c: HCursor): Decoder.Result[URL] =
      for {
        url <- c.downField("url").as[String]
      } yield URL(url)
  }
}

case class VISIT(
  timestamp: String
)

object VISIT {
  implicit val personDecoder: Decoder[VISIT] = new Decoder[VISIT] {
    override def apply(c: HCursor): Decoder.Result[VISIT] =
      for {
        timestamp <- c.downField("timestamp").as[String]
      } yield VISIT(timestamp)
  }
}
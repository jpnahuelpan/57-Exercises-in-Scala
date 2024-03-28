package src.exe_55

import io.circe.{Decoder, HCursor}

case class TEXT(
  text: String
)

object TEXT {
  implicit val personDecoder: Decoder[TEXT] = new Decoder[TEXT] {
    override def apply(c: HCursor): Decoder.Result[TEXT] =
      for {
        text <- c.downField("text").as[String]
      } yield TEXT(text)
  }
}

case class TEXT_UPDATED(
  id: String,
  text: String
)

object TEXT_UPDATED {
  implicit val personDecoder: Decoder[TEXT_UPDATED] = new Decoder[TEXT_UPDATED] {
    override def apply(c: HCursor): Decoder.Result[TEXT_UPDATED] =
      for {
        id <- c.downField("id").as[String]
        text <- c.downField("text").as[String]
      } yield TEXT_UPDATED(id, text)
  }
}

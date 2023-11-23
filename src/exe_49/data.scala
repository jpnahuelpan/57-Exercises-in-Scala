package src.exe_49

case class Media(m: String)

case class ImageData(
  title: String,
  link: String,
  media: Media,
  date_taken: String,
  description: String,
  published: String,
  author: String,
  author_id: String,
  tags: String
)

case class DataImages(
  title: String,
  link: String,
  description: String,
  modified: String,
  generator: String,
  items: List[ImageData]
)

// extracted data

case class ExtractedImgData(
  imgUrl: String,
  width: Int,
  height: Int
)
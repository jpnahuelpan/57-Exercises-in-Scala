
/** Word Frequency Finder
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that reads in a file and counts the frequency of words in the file.
  * 
  */
@main def exercise45(): Unit =
  val filePath = "data/exe45/words.txt"
  val data = readData(filePath)
  histogram(data)

def readData(fileName: String): List[String] =
  import scala.io.Source.fromFile
  import scala.collection.mutable.ListBuffer
  var data: ListBuffer[String] = ListBuffer()
  val file = fromFile(fileName)
  for line <- file.getLines do
    val wordsListLine = line.split(" ").map(_.trim).toList
    wordsListLine.map(newWord => data.addOne(newWord))
  file.close()
  data.toList

def countWords(words: List[String]): List[(String, Int)] =
  words.groupBy(x => x).map((k, v) => (k, v.length)).toList.sortBy(-_(1))

def printWord(word: String, frequency: Int): Unit =
  val space = 10 - word.length
  println(s"$word:" + " " * space + "*" * frequency)

def histogram(words: List[String]): Unit =
  val countedWords = countWords(words)
  countedWords.foreach((word, frequency) =>
    printWord(word, frequency)
  )

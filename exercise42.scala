
/** Parsing a Data File
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Process the records and display the results formatted as a
  * table, evenly spaced.
  */
@main def exercise42(): Unit =
  val inputFile = "data/exe42/input.csv"
  val data = readData(inputFile)
  val sortedData = sortData(data)
  printTable(sortedData)

def readData(fileName: String): List[Map[String, String]] =
  import scala.io.Source.fromFile
  import scala.collection.mutable.ListBuffer
  val data: ListBuffer[Map[String, String]] = ListBuffer()
  val file = fromFile(fileName)
  for line <- file.getLines do
    val cols = line.split(",").map(_.trim)
    data.addOne(Map(
        "Last" -> cols(0),
        "First" -> cols(1),
        "Salary" -> cols(2)
      )
    )
  file.close()
  data.toList

def sortData(data: List[Map[String, String]]): List[Map[String, String]] =
  data.sortBy(s => s("Salary").toInt)

def printTable(data: List[Map[String, String]]): Unit =
  val lastSpace = data.map(_("Last")).maxBy(_.length).length + 2
  val firstSpace = data.map(_("First")).maxBy(_.length).length + 2
  val header = "Last" + " " * (lastSpace - 4) + "First" + " " * (lastSpace - 5) + "Salary"
  println(header)
  println("-" * header.length)
  for line <- data do
    val space1 = " " * (lastSpace - line("Last").length)
    val space2 = " " * (lastSpace - line("First").length)
    println(s"${line("Last")}$space1${line("First")}$space2${line("Salary")}")

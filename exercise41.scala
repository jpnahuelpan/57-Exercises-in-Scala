
/** Name Sorter
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Then print the sorted list to a file.
  */
@main def exercise41(): Unit =
  val inputFile = "data/exe_41_input.txt"
  val outputFile = "data/exe_41_output.txt"
  val data = readData(inputFile)
  val sortedData = sortData(data)
  printTable(sortedData, outputFile)
  saveData(sortedData, outputFile)

def readData(fileName: String): List[String] =
  import scala.io.Source.{fromFile}
  import scala.collection.mutable.ListBuffer

  val data: ListBuffer[String] = ListBuffer()
  for line <- fromFile(fileName).getLines do
    data.addOne(line)
  data.toList

def saveData(data: List[String], fileName: String): Unit =
  import java.io.PrintWriter
  val output = new PrintWriter(fileName)
  for line <- data do
    output.write(s"$line\n")
  output.close()

def sortData(data: List[String]): List[String] =
  data.sortBy(s => s.substring(0, s.length))

def printTable(data: List[String], outputFile: String): Unit =
  val l = data.length
  println(s"\nTotal of $l names")
  println("-" * 16)
  for line <- data do
    println(line)
  println(s"\nThis data was saved in $outputFile\n")

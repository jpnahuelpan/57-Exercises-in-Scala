import scala.io.StdIn.readLine
import src.exe_39_40.{dataSet, printTable}

/** Filtering Records
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that lets a user locate all records that match
  * the search string by comparing the search string to the first
  * or last name field.
  * 
  * Note:
  * Execution comand should be:
  *   - scala-cli exercise39.scala src/exe_39_40
  *   or (if you want)
  *   - scala-cli exercise39.scala src
  */
@main def exercise40(): Unit =
  printTable(dataSet)
  val column = readLine("\nEnter a search string: ").toString
  val sortedTable = searchInList(dataSet, column)
  println("")
  printTable(sortedTable)

def searchInList(dataSet: List[Map[String, String]], string: String): List[Map[String, String]] =
  val l = string.length
  val fn = "First Name"
  val ln = "Last Name"
  dataSet.filter(s => s(fn).substring(0, l) == string | s(ln).substring(0, l) == string)

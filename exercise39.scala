import scala.io.StdIn.readLine
import src.exe_39_40.{dataSet, validateInput, printTable}

/** Sorting Records
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that sorts all employees by last name and
  * prints them to the screen in a tabular format.
  * 
  * Note:
  * Execution comand should be:
  *   - scala-cli exercise39.scala src/exe_39_40
  *   or (if you want)
  *   - scala-cli exercise39.scala src
  */
@main def exercise39(): Unit =
  var getValues = true
  printTable(dataSet)
  while getValues do
    try
      val column = readLine("\nEnter the column to sort the table (First Name, Position, Separation date): ").toString
      if validateInput(dataSet, column) then
        val sortedTable = sortTable(dataSet, column)
        println("")
        printTable(sortedTable)
        getValues = false
    catch
      case e: Exception => println(e.getMessage())

def sortTable(dataSet: List[Map[String, String]], column: String): List[Map[String, String]] =
  dataSet.sortBy(_(column))

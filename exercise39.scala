import scala.io.StdIn.readLine

/** Sorting Records
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that sorts all employees by last name and
  * prints them to the screen in a tabular format.
  */
@main def exercise39(): Unit =
  var getValues = true
  val dataSet = List(
    Map("First Name" -> "John", "Last Name" -> "Johnson", "Position" -> "Manager", "Separation date" -> "2016-12-31"),
    Map("First Name" -> "Tou", "Last Name" -> "Xiong", "Position" -> "Software Engineer", "Separation date" -> "2016-10-05"),
    Map("First Name" -> "Michaela", "Last Name" -> "Michaelson", "Position" -> "District Manager", "Separation date" -> "2015-12-19"),
    Map("First Name" -> "Jake", "Last Name" -> "Jacobson", "Position" -> "Programmer", "Separation date" -> ""),
    Map("First Name" -> "Jacquelyn", "Last Name" -> "Jackson", "Position" -> "DBA", "Separation date" -> ""),
    Map("First Name" -> "Sally", "Last Name" -> "Weber", "Position" -> "Web Developer", "Separation date" -> "2015-12-18")
  )
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

def validateInput(dataSet: List[Map[String, String]], column: String): Boolean =
  if dataSet(1).contains(column) then
    true
  else
    throw Exception("The entered value does not exist as a column in the table. Try again.\n")

def sortTable(dataSet: List[Map[String, String]], column: String): List[Map[String, String]] =
  dataSet.sortBy(_(column))

def printTable(dataSet: List[Map[String, String]]): Unit =
  println("Name" + " " * 15 + "| Position" + " " * 10 + "| Separation date")
  println("-" * 19 + "|" + "-" * 19 + "|" + "-" * 16)
  for data <- dataSet do
    val sep1 = 19 - (data("First Name").length + data("Last Name").length + 1)
    val sep2 = 19 - (data("Position").length + 2)
    println(s"${data("First Name")} ${data("Last Name")}" +
      " " * sep1 + s"| ${data("Position")} " + " " * sep2 + s"| ${data("Separation date")}")

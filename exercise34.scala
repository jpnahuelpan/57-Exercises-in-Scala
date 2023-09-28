import scala.io.StdIn.readLine
import scala.util.Random.nextInt
import scala.collection.mutable.ArrayBuffer

/** Employee List Removal
  *
  * @author Juan Pablo Nahuelpán
  * program that contains a list of employee
  * names. Print out the list of names when the program runs
  * the first time. Prompt for an employee name and remove
  * that specific name from the list of names.
  */
@main def exercise34(): Unit =
  var getValues = true
  var employeesList = ArrayBuffer(
    "John Smith",
    "Jacke Jackson",
    "Chris Jones",
    "Amanda Cullen",
    "Jeremy Goodwin"
  )
  printEmployees(employeesList)
  while getValues do
    try
      val employeeToRemove = readLine("\nEnter an employee name to remove: ").toString
      if existEmpleyee(employeesList, employeeToRemove) then
        employeesList.subtractOne(employeeToRemove)
      printEmployees(employeesList)
      getValues = false
    catch
      case e: Exception => println(e.getMessage)

def printEmployees(employees: ArrayBuffer[String]): Unit =
  println(s"\nThere are ${employees.length} employees:")
  for employee <- employees do
    println(employee)

def existEmpleyee(employeesList: ArrayBuffer[String], employee: String): Boolean =
  if employeesList.contains(employee) then
    true
  else
    throw Exception("That the name does not exist. Try again.")

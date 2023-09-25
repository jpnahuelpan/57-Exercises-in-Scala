import scala.io.StdIn.readLine
import scala.util.matching.Regex

/** Validating Inputs
  *
  * @author Juan Pablo Nahuelpán
  * 
  * program that prompts for a first name, last name,
  * employee ID, and ZIP code.
  */
@main def exercise27(): Unit =
  var getValues = true
  while getValues do
    try
      val fName = readLine("Enter the first name: ")
      val lName = readLine("Enter the last name: ")
      val zipCode = readLine("Enter the ZIP code: ")
      val employeeID = readLine("Enter an imployee ID: ")
      if validateInput(fName, lName, zipCode, employeeID) then
        println("There were no errors found.")
      getValues = false
    catch
      case e: Exception => println(e.getMessage())

def validateName(name: String): Boolean =
  val regex: Regex = """[A-Za-z\p{Space}]+""".r
  regex.matches(name) & name.length >= 2

def validateFirstName(fName: String): Boolean =
  if validateName(fName) then
    true
  else
    false

def validateLastName(lName: String): Boolean =
  if validateName(lName) then
    true
  else
    false

def namesMsg(name: String, prefix: String): String =
  if name.isEmpty then
    s"\nThe ${prefix} name must be filled in."
  else if name.length < 2 then
    s"\n\"${name}\" is not a valid ${prefix} name. It is too short."
  else
    s"\nNumbers are not allowed in the ${prefix} name."

def validateZipCode(zipCode: String): Boolean =
  val regex: Regex = """(^\d{5}$)|(^\d{5}-\d{4}$)""".r
  if regex.matches(zipCode) then
    true
  else
    false

def validateEmployeeId(employeeID: String): Boolean =
  val regex: Regex = """(^\w{2}-\d{3})""".r
  if regex.matches(employeeID) then
    true
  else
    false

def validateInput(
  fName: String,
  lName: String,
  zipCode: String,
  employeeID: String
): Boolean =
  var msg = ""
  if !validateFirstName(fName) then
    msg += namesMsg(fName, "first")
  if !validateLastName(lName) then
    msg += namesMsg(lName, "last")
  if !validateZipCode(zipCode) then
    msg += "\nThe ZIP code must be numeric."
  if !validateEmployeeId(employeeID) then
    msg += s"\n${employeeID} is not a valid ID."
  if msg.isEmpty then
    true
  else
    throw Exception(msg + " Try again.\n")

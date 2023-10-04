import scala.io.StdIn.readLine
import scala.collection.mutable.ListBuffer

/** Filtering Values
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that prompts for a list of numbers, separated
  * by spaces. Have the program print out a new list
  * containing only the even numbers.
  */
@main def exercise38(): Unit =
  var getValues = true
  while getValues do
    try
      val numbers = readLine("Enter a list of numbers, separated by spaces: ").toString
      val evenNumbers = filterEvenNumbers(toNumberList(numbers))
      println(s"The even number are ${results(evenNumbers)}")
      getValues = false
    catch
      case e: NumberFormatException => println("Non-integer numbers are not allowed, "+
        "and please do not add a space at the beginning or end of the list of numbers. Try again\n")


def toNumberList(numbers: String): List[Int] =
  var numbersList: ListBuffer[Int] = ListBuffer()
  var numStr = ""
  var numInt = 0
  for c <- numbers do
    if c != ' ' then
      numStr += c.toString
    else
      numInt = numStr.toInt
      numbersList.addOne(numInt)
      numStr = ""
  numInt = numStr.toInt
  numbersList.addOne(numInt)
  numbersList.toList

def filterEvenNumbers(numbers: List[Int]): List[Int] =
  for
    n <- numbers
    if n % 2 == 0
  yield n

def results(evenNumbers: List[Int]): String =
  var string = ""
  for n <- evenNumbers do
    string += s"${n} "
  string.updated(string.length - 1, '.')


import scala.io.StdIn.readLine
import scala.collection.mutable.ArrayBuffer
import scala.math.{sqrt, pow}

/** Computing Statistics
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program than print the average time (mean), the
  * minimum time, the maximum time, and the standard deviation.
  */
@main def exercise36(): Unit =
  var getValues = true
  var numList: ArrayBuffer[Int] = ArrayBuffer()
  while getValues do
    try
      val number = readLine("Eneter a number: ")
      if number == "done" then
        printResults(numList)
        getValues = false
      else
        numList.addOne(number.toInt)
    catch
      case e0: NumberFormatException => println("Don't allow any non-integer data entry. Try again.\n")
      case e1: UnsupportedOperationException => println("No number has been entered. Try again.\n")

def mean(numbersList: ArrayBuffer[Int]): Float =
  val sum = numbersList.sum.toFloat
  val n = numbersList.length.toFloat
  sum / n

def std(numbersList: ArrayBuffer[Int]): Float =
  val sum = numbersList.sum.toFloat
  val n = numbersList.length.toFloat
  val m = mean(numbersList)
  val squares = for x <- numbersList yield pow((x - m), 2).toFloat
  sqrt(squares.sum / (n -1)).toFloat

def strNumbers(numbersList: ArrayBuffer[Int]): String =
  var string = "Numbers:"
  for x <- numbersList do
    string += s" ${x},"
  string.dropRight(1)

def printResults(numbersList: ArrayBuffer[Int]): Unit =
  // I used instantiation to catch errors when trying to calculate
  // with an empty array and prevent the messages of already
  // calculated operations from being printed.
  val a = strNumbers(numbersList)
  val b = mean(numbersList)
  val c = numbersList.min
  val d = numbersList.max
  val e = std(numbersList)
  println(a)
  println(s"The average is ${b}.")
  println(s"The minimun is ${c}.")
  println(s"The maximum is ${d}.")
  println(f"The standard deviation is ${e}%.2f.\n")

import scala.io.StdIn.readLine

/** Adding Numbers
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that prompts the user for five numbers and
  * computes the total of the numbers.
  */
@main def exercise28(): Unit =
  val numbers = 5
  var total = 0
  for n <- 1 to numbers do
    try
      total += readLine("Enter the number: ").toInt
    catch
      case e: NumberFormatException => ()
  println(s"The total is ${total}.")
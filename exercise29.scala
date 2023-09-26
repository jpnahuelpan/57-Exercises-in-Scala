import scala.io.StdIn.readLine

/** Handling Bad Input
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Quick calculator that prompts for the rate of return
  * on an investment and calculates how many years it will take
  * to double your investment.
  */
@main def exercise29(): Unit =
  var getValues = true
  while getValues do
    try
      val rate = readLine("What is the rate of return? ").toInt
      val years = 72 / rate
      println(s"It will take ${years} years to double your initial investment.")
      getValues = false
    catch
      case e0: NumberFormatException => println("Don’t allow non-integer values. Try again.\n")
      case e1: ArithmeticException => println("Don’t allow the user to enter 0. Try again.\n")

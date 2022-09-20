import scala.io.StdIn.readLine

/** Comparing Numbers
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that asks for three numbers. Check first to
  * see that all numbers are different. If they’re not different,
  * then exit the program. Otherwise, display the largest number
  * of the three.
  */
@main def main: Unit = {
  var getValues = true
  while (getValues) {
    try {
      println("Enter the first number:")
      val number1 = readLine.toInt
      println("Enter the second number:")
      val number2 = readLine.toInt
      println("Enter the third number:")
      val number3 = readLine.toInt
      // display results
      val max = maxNumber(number1, number2, number3)
      println(s"The largest number is ${max}.\n")
      getValues = false
    }
    catch {
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
    }
  }
}

def maxNumber(number1: Int, number2: Int, number3: Int): Int = {
  if (number1 > number2 && number1 > number3)
    number1
  else if (number2 > number1 && number2 > number3)
    number2
  else
    number3
}

import scala.io.StdIn.readLine

/** Simple Math
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that prompts for two numbers. Print the
  * sum, difference, product, and quotient of those numbers.
  */
@main def exercise5(): Unit =
  println("What is the first number?")
  val number1 = readLine()
  println("What is the second number?")
  val number2 = readLine()
  // operate on numbers
  val sum = number1.toInt + number2.toInt
  val difference = number1.toInt - number2.toInt
  val product = number1.toInt * number2.toInt
  val quotient = number1.toInt / number2.toInt
  println(s"$number1 + $number2 = $sum \n" +
    s"$number1 - $number2 = $difference \n" + 
    s"$number1 * $number2 = $product \n" +
    s"$number1 / $number2 = $quotient \n")

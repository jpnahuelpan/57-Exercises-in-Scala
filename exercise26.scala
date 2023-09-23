import scala.io.StdIn.readLine
import scala.math.{log, pow}

/** Months to Pay Off a Credit Card
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that will help you determine how many
  * months it will take to pay off a credit card balance.
  */
@main def exercise26(): Unit =
  var getValues = true
  while getValues do
    try
      val balance = readLine("What is rour balance? ").toFloat
      val APR = readLine("What is the APR on the card (as a percent)? ").toFloat
      val montlyPayment = readLine("What is the montly payment you can make? ").toFloat
      val months = calculateMonthsUntilPaidOff(((APR * 0.01) / 365), balance, montlyPayment)
      println(s"It will take you ${months} months to pay off this card.")
      getValues = false
    catch
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")

/** calculate months until paid off
  * 
  * @params i is the daily rate (APR divided by 365)
  * @params b is the balance of credit card
  * @params p is the monthly payment
  * @return the number of months
  */
def calculateMonthsUntilPaidOff(i: Double, b: Float, p: Float): Int =
  val x = -(1 / 30.0)
  val y = log(1 + ((b / p) * (1 - pow(1 + i, 30))))
  val z = log(1 + i)
  (x * (y / z)).toInt

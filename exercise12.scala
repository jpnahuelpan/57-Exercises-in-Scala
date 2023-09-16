import scala.io.StdIn.readLine

/** Computing Simple Interest
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that computes simple interest. Prompt for
  * the principal amount, the rate as a percentage, and the time,
  * and display the amount accrued (principal + interest).
  */
@main def exercise12(): Unit =
  var getValues = true
  var fa = 0.0f
  while getValues do
    try
      println("Enter the principal:")
      val principal = readLine.toFloat
      println("Enter the rate of interest:")
      val rateInterest = readLine.toFloat
      println("Enter the number of years:")
      val years = readLine.toInt
      // Calculate the amount at the end of the investment and display it.
      for i <- 0 to years do
        fa = calculateSimpleInterest(principal, rateInterest, i)
        println(s"After $i years at $rateInterest%, the investiment will \n" +
          f"be worth $$$fa%.2f" + "\n")
      getValues = false
    catch
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")

def calculateSimpleInterest(principal: Float, rateInterest: Float, years: Int): Float =
  principal * (1 + ((rateInterest / 100)* years))

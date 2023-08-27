import scala.io.StdIn.readLine
import scala.math.pow

/** Determining Compound Interest
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program to compute the value of an investment
  * compounded over time. The program should ask for the
  * starting amount, the number of years to invest, the interest
  * rate, and the number of periods per year to compound.
  */
object exercise13 {
  def main(args: Array[String]): Unit = {
    var getValues = true
    var fa = 0.0d
    while (getValues) {
        try {
          println("What is the principal amount?")
          val principal = readLine.toFloat
          println("What is the rate?")
          val rateInterest = readLine.toFloat
          println("What is the number of years?")
          val years = readLine.toInt
          println("What is the number of times the interest" +
            "is compounded per year?")
          val timesInterestCompoundedPerYear = readLine.toInt
          // Calculate the amount at the end of the investment and display it.
          fa = calculateCompoundInterest(principal, rateInterest, years, timesInterestCompoundedPerYear)
          println(s"$principal invested at $rateInterest% for $years years \n" +
            f"compounded $timesInterestCompoundedPerYear time per year is  $$$fa%.2f." + "\n")
          getValues = false
        }
        catch {
          case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
        }
    }
  }
}

def calculateCompoundInterest(principal: Float, rateInterest: Float, years: Int, ticpy: Int): Double = {
  principal * pow((1 + ((rateInterest / 100)/ ticpy)), (ticpy * years))
}

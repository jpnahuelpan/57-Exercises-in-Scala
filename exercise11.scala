import scala.io.StdIn.readLine

/** Currency Conversion
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that converts currency. Specifically, convert
  * euros to U.S. dollars. Prompt for the amount of money in
  * euros you have, and prompt for the current exchange rate
  * of the euro. Print out the new amount in U.S. dollars.
  */
object exercise11 {
  def main(args: Array[String]): Unit = {
    var getValues = true
    while (getValues) {
        try {
          println("How many euros are you exchanging?")
          val euros = readLine.toFloat
          println("What is the exchange rate?")
          val exchangeRateEuro = readLine.toFloat
          // Calculate dollars
          val dollars = eurosToDollars(euros, exchangeRateEuro)
          println(f"$euros%.2f euros at an exchange rate of $exchangeRateEuro is" + "\n" +
            f"$dollars%.2f U.S. dollars." + "\n")
          getValues = false
        }
        catch {
          case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
        }
    }
  }
}

def eurosToDollars(euros: Float, exchangeRateEuro: Float): Float = {
  val exchangeRateDollar = (euros * exchangeRateEuro) / 111.38f // hardcoded exchange rate
  (euros * exchangeRateEuro) / exchangeRateDollar
}
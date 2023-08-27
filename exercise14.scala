import scala.io.StdIn.readLine
import util.control.Breaks._

/** Tax Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program to compute the tax on an order
  * amount. The program should prompt for the order amount
  * and the state. If the state is “WI,” then the order must be
  * charged 5.5% tax. The program should display the subtotal,
  * tax, and total for Wisconsin residents but display just the
  * total for non-residents.
  */
object exercise14 {
  def main(args: Array[String]): Unit = {
    val Wisconsin = List("WI", "wi", "wI", "Wi", "Wisconsin", "wisconsin", "WISCONSIN")// hard code
    val taxPercent = 0.055f
    var getValues = true
    var total = 0.0f
    breakable{
      while (getValues) {
          try {
            println("What is the order amount?")
            val orderAmount = readLine.toFloat
            println("What is the state?")
            val state = readLine.toString
            // Display outputs.
            if(Wisconsin.contains(state)){
              val tax = orderAmount * taxPercent
              total = orderAmount + tax
              println(f"The subtotal is $$$orderAmount%.2f." + "\n" +
                f"The tax is $$$tax%.2f." + "\n" +
                f"The total is $$$total%.2f." + " \n")
                break
            }
            total = orderAmount
            println(f"The total is $$$total%.2f." + " \n")
            getValues = false
          }
          catch {
            case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
          }
      }
    }
  }
}

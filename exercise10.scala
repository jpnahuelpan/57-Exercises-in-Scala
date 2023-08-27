import scala.io.StdIn.readLine

/** Self-Checkout
  *
  * @author Juan Pablo Nahuelpán
  *
  * Simple self-checkout system. Prompt for the prices
  * and quantities of three items. Calculate the subtotal of the
  * items. Then calculate the tax using a tax rate of 5.5%. Print
  * out the line items with the quantity and total, and then print
  * out the subtotal, tax amount, and total.
  */
object exercise10 {
  def main(args: Array[String]): Unit = {
    var getValues = true
    val taxRate = 0.055f
    while (getValues) {
        try {
          println("Enter the price of item 1:")
          val price1 = readLine.toFloat
          println("Enter the quantity of item 1:")
          val quantity1 = readLine.toInt
          println("Enter the price of item 2:")
          val price2 = readLine.toFloat
          println("Enter the quantity of item 2:")
          val quantity2 = readLine.toInt
          println("Enter the price of item 3:")
          val price3 = readLine.toFloat
          println("Enter the quantity of item 3:")
          val quantity3 = readLine.toInt
          // Calculate the subtotal
          val subtotal = (price1 * quantity1) + (price2 * quantity2) + (price3 * quantity3)
          // Calculate the tax
          val tax = subtotal * taxRate
          // Calculate the total
          val total = subtotal + tax
          // Display the line items
          println(f"Subtotal: $$$subtotal%.2f" + "\n" +
            f"Tax: $$$tax%.2f" + "\n" +
            f"Total: $$$total%.2f" + "\n")
          getValues = false
        }
        catch {
          case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
        }
    }
  }
}

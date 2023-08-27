import scala.io.StdIn.readLine

/** Multistate Sales Tax Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program prompts the user for the order
  * amount and the state where the order
  * will be shipped.
  */
object exercise20 {
  def main(args: Array[String]): Unit = {
    val dataStates = Map(
      "wisconsin" -> "WI",
      "wi" -> "WI",
      "illinois" -> "IL",
      "il" -> "IL",
      )
    val dataCounties = Map(
      "WI" -> List(
        "eau claire county",
        "dunn county"
      ),
      "IL" -> List(
        "adams county",
        "alexander county"
      )
    )
    var getValues = true
    while (getValues) {
      try {
        println("What is the order amount?")
        val amount = readLine.toFloat
        println("What state do you live in?")
        val state: String = dataStates(readLine.toLowerCase)
        println("What's county do you live in?")
        val county = readLine.toLowerCase
        // display results
        if (dataCounties(state).contains(county)) then
          val tax = wiCountiestax(amount, county)
          displayTaxAndTotal(amount, tax)
        else
          println(s"There are no records for the entered county, therefore the tax for state of ${state} will be applied.")
          val tax = statesTax(amount, state)
          displayTaxAndTotal(amount, tax)
        getValues = false
      }
      catch {
        case e0: NumberFormatException => println("Invalid input. Please try again. \n\n")
        case e1: NoSuchElementException => println("There are no records for the entered state. Please try again. \n\n")
      }
    }
  }
}

def statesTax(amount: Float, state: String): Float = {
  if (state == "WI") then 
    amount * 0.05f
  else if (state == "IL") then
    amount * 0.08f
  else
    0.0
}

def wiCountiestax(amount: Float, county: String): Float = {
  val dataTax = Map(
    "eau claire county" -> 0.055f,
    "dunn county" -> 0.054f,
  )// hardcode xd
  val tax: Float = dataTax.getOrElse(county, 0)
  amount * tax
}

def displayTaxAndTotal(amount: Float, tax: Float): Unit = {
  val total = amount + tax
  if (tax == 0.0) then
    println(f"The total is $$${total}%.2f." + "\n")
  else
    println(f"The tax is $$${tax}%.2f.")
    println(f"The total is $$${total}%.2f." + "\n")
}

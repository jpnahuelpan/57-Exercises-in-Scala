import scala.io.StdIn.readLine

/** Paint Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program to Calculate gallons of paint needed to paint the ceiling of a
  * room. Prompt for the length and width, and assume one
  * gallon covers 350 square feet. Display the number of gallons
  * needed to paint the ceiling as a whole number.
  */
object exercise9 {
  def main(args: Array[String]): Unit = {
    var getValues = true
    while (getValues) {
      try {
        println("Enter the length of the room in feet: ")
        val length = readLine.toInt
        println("Enter the width of the room in feet: ")
        val width = readLine.toInt
        val area = length * width
        val gallons = paintNeeded(area)
        println(s"You will need to purchase $gallons gallons of \n" +
          s"paint to cover $area square feet. \n")
        getValues = false
      } catch {
        case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
      }
    }
  }
}

def paintNeeded(area: Int): Int = {
  val conversionRate = 350
  var gallons = area / conversionRate
  if (area % conversionRate != 0) {
    gallons += 1
  }
  gallons
}

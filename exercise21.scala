import scala.io.StdIn.readLine

/** Numbers to Names
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that converts a number from 1 to 12 to the
  * corresponding month.
  */
object exercise21 {
  def main(args: Array[String]): Unit = {
    var getValues = true
    while (getValues) {
      try {
        println("Please enter the number of the month:")
        val month = readLine.toInt
        // display results
        val monthName = numberToMonth(month)
        println(s"The name of the month is ${monthName}.\n")
        getValues = false
      }
      catch {
        case e0: NumberFormatException => println("Invalid input. Please try again. \n\n")
        case e1: MatchError => println("There are only 12 months. Please try again. \n\n")
      }
    }
  }
}

def numberToMonth(number: Int): String = number match {
    case 1  => "January"
    case 2  => "February"
    case 3  => "March"
    case 4  => "April"
    case 5  => "May"
    case 6  => "June"
    case 7  => "July"
    case 8  => "August"
    case 9  => "September"
    case 10 => "October"
    case 11 => "November"
    case 12 => "December"
}

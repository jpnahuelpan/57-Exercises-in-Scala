import scala.io.StdIn.readLine
import java.time.LocalDate

/** Retirement Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that determines how many years you have
  * left until retirement and the year you can retire. It should
  * prompt for your current age and the age you want to retire
  * and display the output.
  */
@main def main: Unit = {
  println("What is your current age?");
  val currentAge = readLine();
  println("At what age would you like to retire?");
  val retireAge = readLine();
  val currentYear = LocalDate.now.getYear;
  // operate on numbers
  val yearsLeft = retireAge.toInt - currentAge.toInt;
  val retireYear = currentYear + yearsLeft;
  println(s"You have $yearsLeft years left until you can retire. \n" +
    s"It's $currentYear, so you can retire in $retireYear. \n");
}
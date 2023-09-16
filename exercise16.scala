import scala.io.StdIn.readLine

/** Legal Driving Age
  *
  * @author Juan Pablo Nahuelpán
  *
  * Pogram that asks the user for their age and compare
  * it to the legal driving age of sixteen. If the user is sixteen or
  * older, then the program should display “You are old enough
  * to legally drive.” If the user is under sixteen, the program
  * should display “You are not old enough to legally drive.”
  */
@main def exercise16(): Unit =
  var getValues = true
  while getValues do
    try
      println("What is your age?")
      val age = readLine.toInt
      val msg = if (age >= 16) "are" else "are not"
      println(s"You ${msg} enough to legally drive.")
      getValues = false
    catch
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")

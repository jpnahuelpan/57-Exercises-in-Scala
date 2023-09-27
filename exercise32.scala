import scala.io.StdIn.readLine
import scala.util.Random.nextInt

/** Guess the Number Game
  *
  * @author Juan Pablo Nahuelpán
  * 
  * game that has three levels of difficulty. The first
  * level of difficulty would be a number between 1 and 10.
  * The second difficulty set would be between 1 and 100.
  * The third difficulty set would be between 1 and 1000.
  */
@main def exercise32(): Unit =
  var getValues = true
  print("Let's play Guess the Number.")
  while getValues do
    try
      var count = 1
      val difficulty = readLine("Pick a difficulty level (1, 2, or 3): ").toInt
      validateDifficulty(difficulty)
      var number = getNumber("I have my number. What's your guess? ")
      val random = generateNumber(difficulty)
      while number != random do
        number = getNumber(guessMsg(number, random))
        count += 1
      println(s"You got it in ${count} guesses!")
      val playAgain = readLine("Play again <yes | no>: ").toString
      if playAgain != "yes" & playAgain != "y" then
        getValues = false
    catch
      case e0: NumberFormatException => println("Don't allow any non-integer data entry. Try again.\n")
      case e1: Exception => println(e1.getMessage())

def validateDifficulty(diff: Int): Unit =
  if diff > 3 | diff < 1 then
    throw Exception("The selected difficulty level is not valid. Try again.\n")

def getNumber(msg: String): Int =
  try
    readLine(msg).toInt
  catch
    case e: NumberFormatException => 0

def generateNumber(difficulty: Int): Int =
  if difficulty == 1 then
    nextInt(10) + 1
  else if difficulty == 2 then
    nextInt(100) + 1
  else
    nextInt(1000) + 1

def guessMsg(number: Int, random: Int): String =
  if number == 0 then
    "Wrong entry. Guess again: "
  else if number > random then
    "Too high. Guess again: "
  else if number < random then
    "Too low. Guess again: "
  else
    "..."

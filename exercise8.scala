import scala.io.StdIn.readLine

/** Pizza Party
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program to evenly divide pizzas. Prompt for the
  * number of people, the number of pizzas, and the number of
  * slices per pizza. Ensure that the number of pieces comes out
  * even. Display the number of pieces of pizza each person
  * should get. If there are leftovers, show the number of leftover
  * pieces.
  */
object exercise8 {
  def main(args: Array[String]): Unit = {
    val piecesPerPizza = 8;
    var getValue = true;
    while (getValue) {
      println("How many people?");
      val people = readLine();
      println("How many pizzas do you have?");
      val pizzas = readLine();
      // validate input
      try {
          val peopleInt = people.toInt
          val pizzasInt = pizzas.toInt
          println(s"$people people with $pizzas pizza${pluralization(pizzasInt)}.");
          // conversion square feet to square meters.
          val totalPiecesPizzas = pizzasInt * piecesPerPizza;
          val totalPiecesPerPerson = totalPiecesPizzas / peopleInt;
          val leftoverPieces = totalPiecesPizzas % peopleInt;
          // display results
          println(s"Each person gets $totalPiecesPerPerson " +
            s"piece${pluralization(totalPiecesPerPerson)} of pizza. \n" +
            s"There are $leftoverPieces leftover piece${pluralization(leftoverPieces)}. \n");
          getValue = false;
      } catch {
          case e: NumberFormatException => println("Invalid input. Please try again. \n\n");
      }
    }
  }
}

/**
  * Pluralization
  *
  * @param number number to check
  * @return String: singular or plural depending on the number (using implicit return)
  */
def pluralization(number: Int): String = {
  if (number == 1) {
    "";
  } else {
    "s";
  }
}

import scala.io.StdIn.readLine

/** Area of a Rectangular Room
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that calculates the area of a room. Prompt
  * the user for the length and width of the room in feet. Then
  * display the area in both square feet and square meters.
  */
@main def main: Unit = {
  var lrf: Float = 0.0f;
  var wrf: Float = 0.0f; 
  println("What is the length of the room in feet?");
  val lengthRoomFeet = readLine();
  println("What is the width of the room in feet?");
  val widthRoomFeet = readLine();
  // validate input
  try {
      lrf = lengthRoomFeet.toFloat
      wrf = widthRoomFeet.toFloat
      println(s"You entered dimensions of $lengthRoomFeet feet by $widthRoomFeet feet.");
      // conversion square feet to square meters.
      val squareFeet = lrf * wrf;
      val squareMeters = squareFeetToSquareMeters(squareFeet);
      // display results
      println("The area is \n" +
        s"$squareFeet square feet \n" +
        s"$squareMeters square meters. \n");
  } catch {
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
      main
  }
}

def squareFeetToSquareMeters(squareFeet: Float): Float = {
  val conversionFactor = 0.09290304f;
  squareFeet * conversionFactor;
}
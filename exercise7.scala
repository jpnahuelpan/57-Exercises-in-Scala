import scala.io.StdIn.readLine

/** Area of a Rectangular Room
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that calculates the area of a room. Prompt
  * the user for the length and width of the room in feet. Then
  * display the area in both square feet and square meters.
  */
@main def exercise7(): Unit =
  // validate input
  val (lrf, wrf) = validateInput
  println(f"You entered dimensions of ${lrf} feet by ${wrf} feet.")
  // conversion square feet to square meters.
  val squareFeet = lrf * wrf
  val squareMeters = squareFeetToSquareMeters(squareFeet)
  // display results
  println("The area is \n" +
    s"$squareFeet square feet \n" +
    s"$squareMeters square meters. \n")

def validateInput: (Float, Float) =
  try
    println("What is the length of the room in feet?")
    val lengthRoomFeet = readLine().toFloat
    println("What is the width of the room in feet?")
    val widthRoomFeet = readLine().toFloat
    return (lengthRoomFeet, widthRoomFeet)
  catch
    case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
    validateInput

def squareFeetToSquareMeters(squareFeet: Float): Float =
  val conversionFactor = 0.09290304f
  squareFeet * conversionFactor

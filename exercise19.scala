import scala.io.StdIn.readLine

/** BMI Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program to calculate the body mass index (BMI)
  * for a person using the person’s height in inches and weight
  * in pounds. The program should prompt the user for weight
  * and height.
  */
@main def main: Unit = {
  var getValues = true
  while (getValues) {
    try {
      println("What's your height(inches)?")
      val height = readLine.toFloat
      println("What's your weight(pounds)?")
      val weight = readLine.toFloat
      val getBMI = BMI(height, weight)
      // Display BMI.
      printBMI(getBMI)
      getValues = false
    }
    catch {
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
    }
  }
}

def BMI(height: Float, weight: Float): Float = {
  (weight /(height * height)) * 703
}

def printBMI(bmi: Float): Unit = {
  println(s"Your BMI is ${bmi}.")
  if (bmi > 18.5f & bmi < 25.0f) then
    println("You are within the ideal weight range.")
  else
    println(s"Your are ${stringOutRange(bmi)}. You should see your doctor.\n")
}

def stringOutRange(bmi: Float): String = {
  if (bmi < 18.5f) then
    return "underweight"
  else if (bmi > 25.0f) then
     "overweight"
  else
    "...."
}


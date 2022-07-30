import scala.io.StdIn.readLine

/** Blood Alcohol Calculator
  *
  * @author Juan Pablo Nahuelpán
  *
  * Pogram that prompts for your weight, gender,
  * number of drinks, the amount of alcohol by volume of the
  * drinks consumed, and the amount of time since your last
  * drink.
  */
@main def main: Unit = {
  val genders = List("female", "male")
  var getValues = true
  while (getValues) {
    try {
      println("What's your gender?")
      val gender = readLine.toLowerCase
      if (genders.contains(gender)){
        println("What's your body weight(pounds)?")
        val W = readLine.toFloat
        println("how many drinks have you consumed?")
        val A = drinkToOz(readLine.toFloat) // convert drinks to oz
        println("What's the number of hours since your last drink?")
        val H = readLine.toFloat // Hours
        val r = if (gender == "male") 0.73f else 0.66f
        val bac = BAC(A, W, H, r)
        println(s"You BAC is ${bac}.")
      } else{
        throw Exception()
      }
      getValues = false
    }
    catch {
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
      case e: Exception => println("Input a valid gender (female or male), thanks. \n\n")
    }
  }
}

def drinkToOz(drinks: Float): Float = {
  val volumeByDrink = 3.4f // oz
  (drinks * volumeByDrink)
}

def BAC(A: Float, W: Float, H: Float, r: Float): Float = {
  ((A * 5.14f) / (W * r)) - (0.015f * H)
}


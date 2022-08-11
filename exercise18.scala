import scala.io.StdIn.readLine

/** Temperature Converter
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that converts temperatures from Fahren-
  * heit to Celsius or from Celsius to Fahrenheit. Prompt for the
  * starting temperature. The program should prompt for the
  * type of conversion and then perform the conversion.
  */
@main def main: Unit = {
  val temperatures = List("C", "F")
  var getValues = true
  while (getValues) {
    try {
      println("Press C to convert from Fahrenheit to Celsius.\n" +
        "Press F to convert from Celsius to Fahrenheit.\n" +
        "Your choice: ")
      var choice = readLine.toUpperCase
      if (temperatures.contains(choice)) {
        getTemperature(choice)
        val temperature = readLine.toFloat
        val result = conversion(choice, temperature)
        PrintResult(choice, result)
      } else{
        throw Exception()
      }
      
      getValues = false
    }
    catch {
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")
      case e: Exception => println("Input a valid temperatures (C or F), thanks. \n\n")
    }
  }
}

def getTemperature(choice: String): Unit = {
  val typeTemperature = if (choice == "C") "Fahrenheit" else "Celsius"
  println(s"Please enter the temperature in ${typeTemperature}: ")
}

def conversion(choice: String, temperature: Float): Float = {
  if (choice == "C") {
    FahrenheitToCelsius(temperature)
  } else {
    CelsiusToFahrenheit(temperature)
  }
}

def FahrenheitToCelsius(fahrenheit: Float): Float = {
  (fahrenheit - 32) * (5.0f / 9)
}

def CelsiusToFahrenheit(celsius: Float): Float = {
  (celsius * (9.0f / 5)) + 32
}

def PrintResult(choice: String, result: Float): Unit = {
  val typeTemperature = if (choice == "C") "Celsius" else "Fahrenheit"
  println(f"The temperature in ${typeTemperature} is ${result}%.2f.")
}

import scala.io.StdIn.readLine
import scala.math.{round}

/** Karvonen Heart Rate
  *
  * @author Juan Pablo Nahuelpán
  * 
  * program that prompts for
  * your age and your resting heart rate. Use the Karvonen
  * formula to determine the target heart rate based on a range of
  * intensities from 55% to 95%.
  */
@main def exercise31(): Unit =
  var getValues = true
  while getValues do
    try
      val restingHR = readLine("Resting pulse: ").toInt
      val age = readLine("Age: ").toInt
      val rlst = results(restingHR, age)
      table(rlst)
      getValues = false
    catch
      case e: NumberFormatException => println("Don't allows non-integer values. Try again.\n")

def karvonenFormula(restingHR: Int, age: Int, intensity: Float): Int =
  round((((220 - age) - restingHR) * intensity) + restingHR)

def results(restingHR: Int, age: Int): List[(Int, Int)] =
  val intensityList = for i <- 55 to 95 by 5 yield i
  for intensity <- intensityList.toList
  yield (intensity, karvonenFormula(restingHR, age, intensity.toFloat/100.toFloat))

def table(lst: List[(Int, Int)]): Unit =
  println("\nIntensity      | Rate     ")
  println("-" * 15 + "|" + "-" * 10)
  for (i, r) <- lst do
    println(s"${i}%" + " " * 12 + "|" + s" ${r} bpm" + " " * 2)

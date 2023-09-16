import scala.io.StdIn.readLine

/** Comparing Numbers
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that asks for three numbers. Check first to
  * see that all numbers are different. If they’re not different,
  * then exit the program. Otherwise, display the largest number
  * of the three.
  */
@main def exercise22(): Unit =
  var getValues = true
  var nums: List[Int] = List()
  var i = 0
  while getValues do
    try
      println(s"Enter the ${i}th number:")
      val num = readLine.toInt
      if !nums.contains(num) then
        nums = nums :+ num
        println("Do you want to enter another number? <yes | no>")
        val answer = readLine.toLowerCase
        if (answer == "yes" || answer == "y")
          i = i + 1
        else
          getValues = false
      else
        println("This number is already in the list. Please enter a different number \n\n")
    catch
      case e: NumberFormatException => println("Invalid input. Please try again. \n\n")

  // display results
  val max = maxNumber(nums)
  println(s"The largest number is ${max}.\n")

def maxNumber(nums: List[Int]): Int =
  var max = nums(0)
  for num <- nums do
    if num > max then
      max = num
  max

import scala.io.StdIn.readLine
import scala.util.Random.nextInt
import scala.collection.mutable.ArrayBuffer

/** Picking a Winner
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that picks a winner for a contest or prize
  * drawing.
  */
@main def exercise35(): Unit =
  var getValues = true
  var namesList: ArrayBuffer[String] = ArrayBuffer()
  while getValues do
    val name = readLine("Eneter a name: ").toString
    if name == "" then
      val winner = nextInt(namesList.length)
      println(s"The winner is... ${namesList(winner)}")
      getValues = false
    else
      namesList.addOne(name)

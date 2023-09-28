import scala.io.StdIn.readLine
import scala.util.Random.nextInt

/** Magic 8 Ball
  *
  * @author Juan Pablo Nahuelpán
  * 
  * game that prompts for a question and then displays
  * either “Yes,” “No,” “Maybe,” or “Ask again later.”
  */
@main def exercise33(): Unit =
  val responses = List("Yes.", "No.", "Maybe.", "Ask again later.")
  val question = readLine("What's your question? ").toString
  val selected = nextInt(4)
  println(responses(selected))

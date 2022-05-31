import scala.io.StdIn.readLine

/** Mad Lib
  *
  * @author Juan Pablo Nahuelpán
  *
  * program that prompts for a noun,
  * a verb, an adverb, and an adjective and injects those into a
  * story that you create.
  */
@main def main: Unit = {
  println("Enter a noun:");
  val noun = readLine();
  println("Enter a verb:")
  val verb = readLine();
  println("Enter an adjective:");
  val adjective = readLine();
  println("Enter an adverb:");
  val adverb = readLine();
  println(s"Do you $verb your $adjective $noun $adverb? That's hilarious!");
}
import scala.io.StdIn.readLine

/** Printing Quotes
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that prompts for a quote and an author.
  */
@main def exercise3(): Unit =
  println("What is the quote?")
  val quote = readLine()
  println("Who said it?")
  val author = readLine()
  println(author + " says, \"" + quote + "\"")

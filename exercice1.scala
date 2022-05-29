import scala.io.StdIn.readLine
import scala.util.Random

/** Methods for generating random greetings.
  *
  * @author Juan Pablo Nahuelpán
  *
  * First, query the username.
  * It then generates a random greeting using the entered name.
  */
@main def hello: Unit =
  println("What's your name?")
  val name = readLine()
  val greetings: List[String] = List("Hello", "Hi", "What's up", "Yo")
  val randomGreeting = greetings(Random.nextInt(greetings.length))
  println(s"$randomGreeting, $name, nice to meet you!")
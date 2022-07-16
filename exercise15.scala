import scala.io.StdIn.readLine

/** Password Validation
  *
  * @author Juan Pablo Nahuelpán
  *
  * Program that validates user login credentials.
  * The program must prompt the user for a username and
  * password. The program should compare the password given
  * by the user to a known password. If the password matches,
  * the program should display “Welcome!” If it doesn’t match,
  * the program should display “I don’t know you.”
  */
@main def main: Unit = {
  val users = Map(("José" -> 12345), ("Catalina" -> 14325), ("Marcos" -> 17293)) // hardcode xd
  var getValues = true
  while (getValues) {
    try {
      println("Who is the user?")
      val user = readLine.toString
      if (users.contains(user)){
        println("What is the password?")
        val password = readLine.toInt
        if (Some(password) == users.get(user)){
            println("Welcome!")
        }
        else {
          println("I don't know you.")
        }
      }
      else {
        throw Exception()
      }
      getValues = false
    }
    catch {
      case e: NumberFormatException => println("Invalid password. Please try again. \n\n")
      case e: Exception => println("This user don't exist. \n\n")
    }
  }
}

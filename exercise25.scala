import scala.io.StdIn.readLine
import scala.util.boundary, boundary.break
import scala.util.matching.Regex

/** Password Strength Indicator
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that determines the complexity of a given
  * password based on these rules:
  *  • A very weak password contains only numbers and is
  *    fewer than eight characters.
  *  • A weak password contains only letters and is fewer than
  *    eight characters.
  *  • A strong password contains letters and at least one
  *    number and is at least eight characters.
  *  • A very strong password contains letters, numbers, and
  *    special characters and is at least eight characters.
  */
@main def exercise25(): Unit =
  val password = readLine("Enter the password: ")
  val passwordCategory = passwordValidation(password)
  println(s"The \'${password}\' is a ${passwordCategory} password.")


def evaluateLength(string: String): Boolean =
  string.length >= 8

def onlyNumbers(string: String): Boolean =
  val regex = "[0-9]+".r
  regex.matches(string)

def onlyLetters(string: String): Boolean =
  val regex = "[A-Za-z]+".r
  regex.matches(string)

def containsOne(string: String, regex: Regex): Boolean =
  boundary:
    for c <- string do
      if regex.matches(c.toString) then
        boundary.break(true)
    false

def containsSpecialCharacters(string: String): Boolean =
  val regex = "[^A-Za-z0-9]".r
  containsOne(string, regex)

def containsLettersAndNumbers(string: String): Boolean =
  val lettersRegex = "[A-Za-z]".r
  val numbersRegex = "[0-9]".r
  containsOne(string, lettersRegex) && containsOne(string, numbersRegex)


def veryWeakPassword(pass: String): Boolean =
  if onlyNumbers(pass) && !evaluateLength(pass) then
    true
  else
    false

def weakPassword(pass: String): Boolean =
  if onlyLetters(pass) && !evaluateLength(pass) then
    true
  else
    false

def strongPassword(pass: String): Boolean =
  if containsLettersAndNumbers(pass) && !containsSpecialCharacters(pass) && evaluateLength(pass) then
    true
  else
    false

def veryStrongPassword(pass: String): Boolean =
  if containsLettersAndNumbers(pass) && containsSpecialCharacters(pass) && evaluateLength(pass) then
    true
  else
    false

def passwordValidation(pass: String): String =
  if veryWeakPassword(pass) then
    "very weak"
  else if weakPassword(pass) then
    "weak"
  else if strongPassword(pass) then
    "strong"
  else if veryStrongPassword(pass) then
    "very strong"
  else
    val msg = if (onlyLetters(pass)) "long alphabetic" else "long numeric"
    msg

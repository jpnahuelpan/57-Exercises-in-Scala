import scala.io.StdIn.readLine
import scala.util.boundary, boundary.break
import scala.util.matching.Regex

/** Password Strength Indicator
  *
  * @author Juan Pablo Nahuelpán
  * 
  */
@main def exercise25(): Unit =
  val password = readLine("Enter the password: ")
  val passwordCategory = passwordValidation(password)
  println(s"The \'${password}\' is a ${passwordCategory} password.")


def evaluateLength(string: String): Boolean =
  string.length >= 8

def onlyNumbers(string: String): Boolean =
  val regex = "[0-9]+".r
  return regex.matches(string)

def onlyLetters(string: String): Boolean =
  val regex = "[A-Za-z]+".r
  return regex.matches(string)

def containsOne(string: String, regex: Regex): Boolean =
  boundary:
    for c <- string do
      if regex.matches(c.toString) then
        boundary.break(true)
    false

def containsSpecialCharacters(string: String): Boolean =
  val regex = "[^A-Za-z0-9]".r
  return containsOne(string, regex)

def containsLettersAndNumbers(string: String): Boolean =
  val lettersRegex = "[A-Za-z]".r
  val numbersRegex = "[0-9]".r
  return containsOne(string, lettersRegex) && containsOne(string, numbersRegex)


def veryWeekPassword(pass: String): Boolean =
  if onlyNumbers(pass) && !evaluateLength(pass) then
    return true
  else
    return false

def weekPassword(pass: String): Boolean =
  if onlyLetters(pass) && !evaluateLength(pass) then
    return true
  else
    return false

def strongPassword(pass: String): Boolean =
  if containsLettersAndNumbers(pass) && !containsSpecialCharacters(pass) && evaluateLength(pass) then
    return true
  else
    return false

def veryStrongPassword(pass: String): Boolean =
  if containsLettersAndNumbers(pass) && containsSpecialCharacters(pass) && evaluateLength(pass) then
    return true
  else
    return false

def passwordValidation(pass: String): String =
  if veryWeekPassword(pass) then
    return "very week"
  else if weekPassword(pass) then
    return "week"
  else if strongPassword(pass) then
    return "strong"
  else if veryStrongPassword(pass) then
    return "very strong"
  else
    val msg = if (onlyLetters(pass)) "long alphabetic" else "long numeric"
    return msg

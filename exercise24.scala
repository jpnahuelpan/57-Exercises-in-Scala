import scala.io.StdIn.readLine

/** Anagram Checker
  *
  * @author Juan Pablo Nahuelpán
  * 
  * program that compares two strings and determines
  * if the two strings are anagrams.
  */
@main def exercise24(): Unit =
  println("Entrer two string and I'll tell yoy if they are anagrams:")
  val string1 = readLine("Enter the first string: ")
  val string2 = readLine("Enter the second string: ")
  if isAnagram(string1.toLowerCase, string2.toLowerCase()) then
    println(s"\"${string1}\" and \"${string2}\" are anagrams.")
  else
    println(s"\"${string1}\" and \"${string2}\" are not anagrams.")

def countChar(string: String): Int =
  var count = 0
  for c <- string do
    count += 1
  return count

def checkLength(string1: String, string2: String): Boolean =
  countChar(string1) == countChar(string2)

def countElements(set: Set[String]): Int =
  var count = 0
  for e <- set do
    count += 1
  return count

def setChar(string: String): Set[String] =
  var setChar: Set[String] = Set()
  for c <- string do
    setChar += c.toString
  return setChar

def checkElements(string1: String, string2: String): Boolean =
  countElements(setChar(string1)) == countElements(setChar(string2))

def isAnagram(string1: String, string2: String): Boolean =
  if checkLength(string1, string2) then
    if checkElements(string1, string2) then
      val stringLength = countChar(string1)
      var string2Copy = string2
      var count = 0
      for c1 <- string1 do
        for c2 <- string2Copy do
          if c1 == c2 then
            count += 1
        string2Copy = string2Copy.replace(s"${c1}", "")
      return count == stringLength
    else
      return false
  else
    return false

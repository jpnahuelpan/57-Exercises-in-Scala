import scala.io.StdIn.readLine
import scala.util.Random.nextInt
import scala.collection.mutable.{ArrayBuffer, Set}

/** Password Generator
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that generates a secure password.
  */
@main def exercise37(): Unit =
  var getValues = true
  while getValues do
    try
      val minLength = readLine("What's the minimum length? ").toInt
      val specialChar = readLine("How many special characters? ").toInt
      val numbers = readLine("How many numbers? ").toInt
      val password = generatePassword(minLength, specialChar, numbers)
      println(s"Your password is \n${password}")
      getValues = false
    catch
      case e: NumberFormatException => println("Don't allow any non-integer data entry. Try again.\n")

def generateIndexs(len: Int, special: Int): List[Int] =
  var index = -1
  var indexs: Set[Int] = Set()
  for _ <- 1 to special do
    while
      index = nextInt(len)
      indexs.contains(index)
    do ()
    indexs += index
  indexs.toList

def toArrayB(string: IndexedSeq[Char]): ArrayBuffer[Char] =
  var ab: ArrayBuffer[Char] = ArrayBuffer()
  for e <- string do
    ab.addOne(e)
  ab

def getOne(typeChar: Int): Char =
  val sc = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/"
  val chars = "qwertyuiopasdfghjklzxcvbnm"
  val numbers = "1234567890"
  val list = List(sc, chars, numbers)
  val typeCharSelected = list(typeChar)
  typeCharSelected(nextInt(typeCharSelected.length))

def generatePassword(len: Int, sc: Int, numbers: Int): String =
  val nums = for _ <- 1 to numbers yield getOne(2)
  val specialChar = for _ <- 1 to sc yield getOne(0)
  val ns: ArrayBuffer[Char] = toArrayB(nums) ++= toArrayB(specialChar)
  println(ns)
  var password = for _ <- " " * len yield getOne(1)
  generateIndexs(len, numbers + sc).foldLeft(password)((s, i) => s.updated(i, ns.remove(ns.length -1)))

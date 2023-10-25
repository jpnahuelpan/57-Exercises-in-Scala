
/** Word Finder
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that reads in a file and look for all occurrences
  * of the word 'utilize'. Replace each occurrence with 'use'.
  * 
  */
@main def exercise45(): Unit =
  val filePath = "data/exe45/input.txt"
  val filePathOutput = "data/exe45/output.txt"
  val inputText = readText(filePath)
  val outputText = newText(inputText, "utilize", "use")
  println(s"Input text: \n $inputText\n")
  println(s"Output text: \n $outputText\n")
  saveText(outputText, filePathOutput)
  println(s"The output text was saved in $filePathOutput\n")

def newText(text: String, wordToReplace: String, newWord: String): String =
  text.replace(wordToReplace, newWord)

def readText(fileName: String): String =
  import scala.io.Source.fromFile
  var text: String = ""
  val file = fromFile(fileName)
  for line <- file.getLines do
    text += " " + line
  file.close()
  text

def saveText(text: String, fileName: String): Unit =
  import java.io.PrintWriter
  val output = new PrintWriter(fileName)
  output.write(s"$text")
  output.close()

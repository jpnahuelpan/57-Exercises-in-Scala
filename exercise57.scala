//> using lib "org.yaml:snakeyaml:2.2"

import org.yaml.snakeyaml.{
  Yaml,
  TypeDescription,
  LoaderOptions
}
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.nodes.Tag

import java.io.{File, FileInputStream}

import scala.io.StdIn.readLine
import scala.util.Random

import src.exe_57.{Questions, Question}

/** Trivia App
  *
  * @author Juan Pablo Nahuelpán
  *
  * Multiple-choice trivia application
  *
  * NOTE:
  *   Execution command should be:
  *     scala-cli exercise57.scala src/exe_57
  *
  */

// Consts
val TAG_NAME = "!Questions"
val PATH_FILE = "data/exe57/data.yml"

@main def exercise57(): Unit =
  var getValues = true
  val questions = getData
  while getValues do
    try
      val rand = new Random
      val idx = rand.nextInt(questions.questions.size())
      val question = getRandomQuestion(questions, idx)
      questions.questions.remove(idx)
      val order = showQuestion(question)
      val answer = readLine("Enter selection: ").toInt
      if List(1, 2).contains(answer) && order == answer then
        println("This is correct.\n")
      else if List(1, 2).contains(answer) && order != answer then
        println("You lose.")
        getValues = false
      else
        println("That option does't exist.\n")
    catch
      case e0: NumberFormatException => println("\nA numerical input is required, please try again.\n")
      case e1: java.lang.IllegalArgumentException => {
        getValues = false
        println("You win!")
      }
      case e: Exception => println(s"\n${e}\n")


def getData: Questions =
  val inputStream = new FileInputStream(new File(PATH_FILE))
  val constructor = new Constructor(classOf[Questions], new LoaderOptions)
  val productDescription = new TypeDescription(classOf[Questions], TAG_NAME)
  productDescription.addPropertyParameters("questions", classOf[Question])
  constructor.addTypeDescription(productDescription)
  val yaml = new Yaml(constructor)

  yaml.load(inputStream).asInstanceOf[Questions]

def getRandomQuestion(questions: Questions, idx: Int): Question =
  // val rand = new Random
  // val idx = rand.nextInt(questions.questions.size())
  questions.questions.get(idx)

def showQuestion(question: Question): Int =
  val rand = new Random
  val opt = 1 + rand.nextInt(2)
  println(s"${question.question}?\n")
  if opt == 1 then
    println(s"1- ${question.answer}")
    println(s"2- ${question.distraction}\n")
    opt
  else
    println(s"1- ${question.distraction}")
    println(s"2- ${question.answer}\n")
    opt

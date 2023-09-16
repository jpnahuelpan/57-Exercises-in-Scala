import scala.io.StdIn.readLine

/** Troubleshooting Car Issues
  *
  * @author Juan Pablo Nahuelpán
  *
  * Create a program that walks the user through
  * troubleshooting issues with a car.
  */
// Global variables
var msgGlobal = "Is the car silent when you turn the key?"
var next = true
@main def exercise23(): Unit =
  val rulesEngine = RulesEngine(List(
      Rule(
        rootCondition,
        rootAction
      ),
      Rule(
        terminalsCorroded,
        terminalsCorrodedAction
      ),
      Rule(
        clickingNoise,
        clickingNoiseAction
      ),
      Rule(
        carCrank,
        carCrankAction
      ),
      Rule(
        startAndDie,
        startAndDieAction
      ),
      Rule(
        haveFuelInjection,
        haveFuelInjectionAction
      )
    )
  )
  while next do
    rulesEngine.inferenceEngine(msgGlobal)


class Rule(
  val condition: (String) => Boolean,
  val action: (String) => Unit
)

/** A rules engine to manage decitions
  * 
  * @Constructor Create a new rule engine with a list of rules.
  * @param _rules List is the rules repository
  */
class RulesEngine(private val _rules: List[Rule]):
  def inferenceEngine(msg: String): Unit =
    for rule <- _rules do
      if rule.condition(msg) then
        rule.action(msg)
end RulesEngine

// Function to print solutions and break the while control structure.
def breakF(): Unit =
  next = false

def printF(msg: String): Unit =
  println(msg)
  breakF()

// Functions to get user answers.
def evaluateAnswer(msg: String): Boolean = msg == "yes" || msg == "y"

def getAnswer(msg: String): Boolean =
  println(s"${msg} <yes | no>")
  val answer = readLine.toLowerCase
  evaluateAnswer(answer)

// Conditions and actions.
def rootCondition(msg: String): Boolean =
  msg == "Is the car silent when you turn the key?"

def rootAction(msg: String): Unit =
  if getAnswer(msg) then
    msgGlobal = "Are the battery terminals corroded?"
  else
    msgGlobal = "Does the car make a clicking noise?"

def terminalsCorroded(msg: String): Boolean =
  msg == "Are the battery terminals corroded?"

def terminalsCorrodedAction(msg: String): Unit =
  if getAnswer(msg) then
    printF("Clean terminals and try starting again.")
  else
    printF("Replace clables and try again.")

def clickingNoise(msg: String): Boolean =
  msg == "Does the car make a clicking noise?"

def clickingNoiseAction(msg: String): Unit =
  if getAnswer(msg) then
    printF("Replace the battery.")
  else
    msgGlobal = "Does the car crank up but fail to start?"

def carCrank(msg: String): Boolean =
  msg == "Does the car crank up but fail to start?"

def carCrankAction(msg: String): Unit =
  if getAnswer(msg) then
    printF("Check spark plug connections.")
  else
    msgGlobal = "Does the engine start and then die?"

def startAndDie(msg: String): Boolean =
  msg == "Does the engine start and then die?"

def startAndDieAction(msg: String): Unit =
  if getAnswer(msg) then
    msgGlobal = "Does your car have fuel injection?"
  else
    next = false

def haveFuelInjection(msg: String): Boolean =
  msg == "Does your car have fuel injection?"

def haveFuelInjectionAction(msg: String): Unit =
  if getAnswer(msg) then
    printF("Get in for service.")
  else
    printF("Check to ensure the choke is opening and closing.")

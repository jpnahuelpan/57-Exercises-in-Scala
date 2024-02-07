//> using lib "org.slf4j:slf4j-nop:2.0.12"
//> using lib "redis.clients:jedis:5.1.0"
//> using lib "com.google.code.gson:gson:2.10.1"

import scala.io.StdIn.readLine
import scala.util.Random

import redis.clients.jedis.JedisPooled
import redis.clients.jedis.exceptions.{
  JedisConnectionException,
  JedisDataException
}
import redis.clients.jedis.search.{
  Document,
  Query,
  FTCreateParams,
  IndexDataType
}
import redis.clients.jedis.search.schemafields.{
  SchemaField,
  TextField,
  NumericField
}

import java.util.{
  List => javaList,
  ArrayList => javaArrayList
}

// to get local time and format
import java.time.{LocalDateTime, Instant, Clock, ZoneId}
import java.time.format.DateTimeFormatter

import com.google.gson.Gson

import src.exe_53.Task


/** Todo List
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Simple command-line todo list program that meets the following specifications:
  *   • Prompt the user to enter a chore or task. Store the task
  *     in a permanent location so that the task persists when
  *     the program is restarted.
  *   • Allow the user to enter as many tasks as desired but
  *     stop entering tasks by entering a blank task. Do not store
  *     the blank task.
  *   • Display all the tasks.
  *   • Allow the user to remove a task, to signify it’s been
  *     completed.
  *
  * Note:
  *   Before execute the program you should init de redis stack server in another
  *   terminal with this command:
  *     redis-stack-server
  *
  *   Execution command should be:
  *     scala-cli exercise53.scala src/exe_53
  *
  *
  *   Library and Guides:
  *   - The script utilizes the server client library for Java and Redis guides.
  *     https://redis.io/docs/install/install-stack/    (Redis stack install doc)
  *     https://redis.io/docs/get-started/document-database/    (Use java examples)
  *     https://javadoc.io/doc/redis.clients/jedis/5.1.0/index.html   (Jedis Java documentation.)
  *
  */

val MAX_LENGTH_TASK = 45 // CHARACTERS

@main def exercise53(): Unit =
  val idxName = "idx:task"
  var getValues = true
  try
    val jedis: JedisPooled = new JedisPooled("localhost", 6379)
    while getValues do
      createInx(jedis, idxName)

      val query: Query = new Query("*").setSortBy("createDate", true)
      val result: javaList[Document] = jedis.ftSearch(idxName, query).getDocuments()
      taskTable(result)
      val changes: String = readLine("Options:\n 1- Create task\n 2- Delete task\n 3- Exit \n Select option <1 | 2 | 3>: ")
      // Add task
      if changes == "1" then
        val formatedTime: Long = getTime
        val task: String = readLine("Enter task name: ")
        if task.length <= MAX_LENGTH_TASK then
          var id = generateRandomId
          while jedis.exists(s"task:$id") do
            id = generateRandomId
          jedis.jsonSetWithEscape(s"task:$id", Task(task, formatedTime))
          println(s"The task was created with id=$id successful.\n")
        else
          println("So much text... try again.\n")
      // Delete task
      else if changes == "2" then
        var idTask = readLine("Enter the taskId <task:xxxx>: ")
        while !jedis.exists(idTask) do
          println("You input don't exist.")
          idTask = readLine("Enter the taskId (task:xxxx): ")
        jedis.del(idTask)
        println(s"Task with id=$idTask was deleted.\n")
      // Exit of program
      else if changes == "3" then
        jedis.close()
        println("Bye...\n")
        getValues = false
      else
        println("Action selected don't exists.\n")

  catch
    case e: JedisConnectionException =>
      println(s"Error: ${e.getMessage}")
      println(s"Give up the redis stack server in another terminal\nand start the program again.")


def getTime: Long =
  val timeNow = Instant.now()
  timeNow.getEpochSecond

def formatTime(unixTime: Long): String =
  val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.systemDefault)
  val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  dateTime.format(format)

def taskTable(data: javaList[Document]): Unit =
  val header = "taskId" + " " * 5 + "Task" + " " * (MAX_LENGTH_TASK - 4) + "Date"
  println(header)
  println("-" * (header.length + 15))
  val gson = new Gson()
  data.forEach(doc =>
    val task = gson.fromJson(doc.getString("$"), classOf[Task])
    val space = MAX_LENGTH_TASK - task.task.length
    println(s"${doc.getId}  ${task.task}" + " "*space + s"${formatTime(task.createDate)}")
  )
  println()

def generateRandomId: String =
  val random = new Random
  val randomNumber = random.nextInt(10000)
  f"$randomNumber%04d"

def createInx(jedis: JedisPooled, name: String): Unit =
  try
    val schema: javaList[SchemaField] = javaArrayList(javaList.of(
      TextField.of("$.task").as("task"),
      NumericField.of("$.createDate").as("createDate"),
    ))
    jedis.ftCreate(name,
      FTCreateParams.createParams()
      .on(IndexDataType.JSON)
      .addPrefix("task:"),
      schema
    )
  catch
    case e: JedisDataException => () //println(s"${e.getMessage}")

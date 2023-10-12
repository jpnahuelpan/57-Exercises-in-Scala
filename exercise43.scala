import scala.io.StdIn.readLine
import java.nio.file.{Files, Paths, FileAlreadyExistsException}

/** Website Generator
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that generates a website skeleton.
  * 
  * Note:
  *   - The default folder for storing the folders of each site is 'skeletons'.
  *   - If the folder already exists, it will throw an error and request the
  *     site name again.
  */
// Global constants
val BASE_PATH = "skeletons/"
@main def exercise43(): Unit =
  var getValues = true
  while getValues do
    try
      val siteName = readLine("Site name: ")
      val author = readLine("Author: ")
      val jsFolder = readLine("Do you want a folder for JavaScript? ").toLowerCase
      val cssFolder = readLine("Do you want a folder for CSS? ").toLowerCase
      createProject(siteName, author, jsFolder, cssFolder)
      getValues = false
    catch
      case e0: FileAlreadyExistsException =>
        println(s"\n Folder ${e0.getMessage} already exists. Try again.\n")

def createIndex(siteName: String, author: String): Unit =
  import java.io.PrintWriter
  val output = new PrintWriter(BASE_PATH + siteName + "/index.html")
  val title = s"<title>$siteName</title>"
  val meta = s"<meta>$author</meta>"
  val forWrite = List(title, meta)
  for line <- forWrite do
    output.write(s"$line\n")
  output.close()
  println(s"Created ./$siteName/index.html")

def createProject(siteName: String, author: String, js: String, css: String): Unit =
  val siteFolder = Paths.get(BASE_PATH + siteName)
  val jsFolder = Paths.get(BASE_PATH + siteName + "/js")
  val cssFolder = Paths.get(BASE_PATH + siteName + "/css")
  Files.createDirectory(siteFolder)
  println(s"Created ./$siteName")
  createIndex(siteName, author)
  if js == "y" | js == "yes" then
    Files.createDirectory(jsFolder)
    println(s"Created ./$siteName" + "/js")
  if css == "y" | css == "yes" then
    Files.createDirectory(cssFolder)
    println(s"Created ./$siteName" + "/css")

//> using lib "org.yaml:snakeyaml:2.2"
//> using lib "com.github.tototoshi::scala-csv:1.3.10"
//> using lib "org.scala-lang.modules::scala-xml:2.3.0"

import org.yaml.snakeyaml.{
  DumperOptions,
  Yaml,
  TypeDescription,
  LoaderOptions
}
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.Representer
import org.yaml.snakeyaml.nodes.Tag

import com.github.tototoshi.csv.{CSVWriter, defaultCSVFormat}

import java.io.{File, FileInputStream, PrintWriter}
import java.lang.NumberFormatException

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readLine

import src.exe_56.{Items, Item}

/** Tracking Inventory
  *
  * @author Juan Pablo Nahuelpán
  *
  * program that tracks your personal inventory. The
  * program should allow you to enter an item, a serial number,
  * and estimated value.
  *
  * NOTE:
  *   Execution command should be:
  *     scala-cli exercise56.scala src/exe_56
  *
  */

// Consts
val TAG_NAME = "!Items"
val PATH_FILE = "data/exe56/data.yml"
val OUTPUT_CSV = "data/exe56/reports/output.csv"
val OUTPUT_HTML = "data/exe56/reports/output.html"

@main def exercise56(): Unit =
  var getValues = true
  while getValues do
    try
      val data = getData
      showData(data)
      val selection = getUserSelection
      if selection == 1 then
        val newItem = getNewItem
        data.items.add(newItem)
        insertData(data)
        println("\nInserted successfully.\n")
      else if selection == 2 then
        makeCsvReport(data)
        makeHtmlReport(data)
        println("\nYou can view the reports in the 'reports' folder:")
        println(s"Csv report: $OUTPUT_CSV")
        println(s"Html report: $OUTPUT_HTML\n")
      else if selection == 3 then
        println("This program is ended.")
        getValues = false
      else
        println("That selection doesn't exist, please try again.")
    catch
      case e0: NumberFormatException => println("\nA numerical input is required, please try again.\n")
      case e: Exception => println(s"\n${e}\n")


def getData: Items =
  val inputStream = new FileInputStream(new File(PATH_FILE))
  val constructor = new Constructor(classOf[Items], new LoaderOptions)
  val productDescription = new TypeDescription(classOf[Items], TAG_NAME)
  productDescription.addPropertyParameters("items", classOf[Item])
  constructor.addTypeDescription(productDescription)
  val yaml = new Yaml(constructor)

  yaml.load(inputStream).asInstanceOf[Items]

def insertData(items: Items): Unit =
  val opciones = new DumperOptions()
  opciones.setPrettyFlow(true)
  opciones.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
  opciones.setIndent(2)

  val representer = new Representer(opciones)
  representer.addClassTag(classOf[Items], new Tag(TAG_NAME))
  representer.addClassTag(classOf[Item], Tag.MAP)

  val yaml = new Yaml(representer)
  val writer = new PrintWriter(new File(PATH_FILE))
  yaml.dump(items, writer)
  writer.close()

def makeCsvReport(items: Items): Unit =
  val data = ListBuffer(items.items.get(0).getKeys)
  items.items.forEach(item =>
    data.addOne(List(item.name, item.serialNumber, s"${item.value}"))
  )
  val reportCSV = new File(OUTPUT_CSV)
  val writer = CSVWriter.open(reportCSV)
  writer.writeAll(data.toList)
  writer.close()

def makeHtmlReport(items: Items): Unit =
  val data: ListBuffer[Item] = ListBuffer()
  items.items.forEach(item =>
    data.addOne(item)
  )
  val tableHTML = {
    <table border="1">
      <thead>
        <tr>
          <th>Name</th>
          <th>Serial Number</th>
          <th>Value</th>
        </tr>
      </thead>
      <tbody>
        {for item <- data yield {
        <tr>
          <td>{item.name}</td>
          <td>{item.serialNumber}</td>
          <td>{item.value}</td>
        </tr>
      }}
      </tbody>
    </table>
  }
  val writer = new PrintWriter(OUTPUT_HTML)
  writer.write("<!DOCTYPE html>\n<html>\n<head>\n<title>Html report</title>\n</head>\n<body>\n")
  writer.write(tableHTML.toString())
  writer.write("\n</body>\n</html>")
  writer.close()

def getColWidths(items: Items): (Int, Int, Int) =
  var colName = items.items.stream()
    .mapToInt(item => item.name.length)
    .max()
    .getAsInt()
  colName = if (colName < "Name".length) then
    "Name".length else colName
  var colSerialNumber = items.items.stream()
    .mapToInt(item => item.serialNumber.length)
    .max()
    .getAsInt()
  colSerialNumber = if (colSerialNumber < "Serial Number".length) then
    "Serial Number".length else colSerialNumber
  var colValue = items.items.stream()
    .mapToInt(item => item.value.toString.length)
    .max()
    .getAsInt()
  colValue = if (colValue < "Value".length) then
    "Value".length else colValue
  (colName, colSerialNumber, colValue)

def titleBar(cn: Int, csn: Int, cv: Int): String =
  val nt = " "* (cn/2) + "Name" + " "* (cn/2)
  val snt = " "* (csn/2 - 6) + "Serial Number" + " "* (csn/2 - 6)
  val vt = " "* (cv/2) + "Value" + " "* (cv/2)
  nt + snt + vt

def space(str: String, len: Int): String =
  " " + str + " "*(len - str.length) + " "

def valuesRow(item: Item, cn: Int, csn: Int, cv: Int): String =
  space(item.name, cn) + space(item.serialNumber, csn) + space(s"$$${item.value.toString}", cv)

def showData(items: Items): Unit =
  val (cn, csn, cv) = getColWidths(items)
  println(titleBar(cn, csn, cv))
  items.items.forEach(item => println(valuesRow(item, cn, csn, cv)))
  println()

def getUserSelection: Int =
  println("Select a option: \n 1- Insert new Item. \n 2- Generate reports. \n 3- Quit.")
  readLine("Enter option: ").toInt

def getNewItem: Item =
  val name = readLine("Enter the Name: ")
  val serialNumber = readLine("Enter the Serial Number: ")
  val value = readLine("Enter the Value: ").toDouble
  Item(name, serialNumber, value)

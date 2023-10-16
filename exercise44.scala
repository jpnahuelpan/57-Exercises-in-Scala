import scala.io.StdIn.readLine
import upickle.default.{
  ReadWriter => RW,
  write,
  read
}

/** Product Search
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that takes a product name as input and
  * retrieves the current price and quantity for that product.
  * 
  * Note:
  *   Execution comand should be:
  *   - scala-cli exercise44.scala --dep com.lihaoyi::upickle:3.1.3 --dep com.lihaoyi::os-lib:0.9.1
  */
@main def exercise44(): Unit =
  val filePath = "data/exe44/data.json"
  var getValues = true
  while getValues do
    try
      val productName = readLine("What is the product name? ")
      val data = getData(filePath)
      val search = searchProduct(data.products, productName)
      if search.nonEmpty then
        val product = search.get
        printProduct(product)
        getValues = false
      else
        val addProduct = requestAddProduct()
        if addProduct then
          val (price, quantity) = requestProductInfo()
          insertProductToJson(filePath, data, productName, price, quantity)
          println("Product added..\n")
    catch
      case e: NumberFormatException => println(s"Is not valid input ${e.getMessage}. Try again.\n")

case class Product(name: String, price: Float, quantity: Int) derives RW

case class Data(products: List[Product]) derives RW

def getData(filePath: String): Data =
  read[Data](os.read(os.pwd / os.RelPath(filePath)))

def searchProduct(products: List[Product], productName: String): Option[Product] =
  products.find(_.name.toLowerCase == productName.toLowerCase)

def printProduct(product: Product): Unit =
  println(s"Name: ${product.name}\n" +
    f"Price: ${product.price}%.2f\n" +
    s"Quantity: ${product.quantity}")

def requestAddProduct(): Boolean =
  val answer = readLine("Do you want add it to the Json? ")
  if answer == "yes" | answer == "y" then
    true
  else
    false

def requestProductInfo(): (Float, Int) =
  val price = readLine("Enter price (float): ").toFloat
  val quantity = readLine("Enter quantity (integer): ").toInt
  (price, quantity)

def insertProductToJson(
  filePath: String,
  data: Data,
  productName: String,
  price: Float,
  quantity: Int
): Unit =
  import java.io.FileWriter
  val newData = data.copy(products=Product(productName, price, quantity) :: data.products)
  val json: String = write(newData, indent=4)
  val file = FileWriter(filePath)
  file.write(json)
  file.close

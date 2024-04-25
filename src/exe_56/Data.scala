package src.exe_56

import scala.beans.BeanProperty
import java.util.{
  Arrays,
  List => javaList
}

class Item(
  @BeanProperty var name: String,
  @BeanProperty var serialNumber: String,
  @BeanProperty var value: Double):
  override def toString = s"Item(name=$name, serialNumber=$serialNumber, value=$value)"
  def this() = this("", "", 0.0)
  def getKeys: List[String] = List("name", "serialNuber", "value")

class Items(@BeanProperty var items: javaList[Item]):
  override def toString = s"Items(items=$items)"
  def this() = this(Arrays.asList(Item("", "", 0.0)))
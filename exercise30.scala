import scala.io.StdIn.readLine

/** Multiplication Table
  *
  * @author Juan Pablo Nahuelpán
  * 
  * program that generates multiplication tables for
  * the numbers 0 through 12.
  */
@main def exercise30(): Unit =
  println("Multiplication table (12 x 12):\n\n")
  matrixTable()
  // listTable() // you can uncoment if you want a large list.

def listTable(): Unit =
  println("List table: \n")
  for
    i <- 0 to 12
    j <- 1 to 12
  do
    println(s"${i} x ${j} = ${i * j}")

def box(string: String): String =
  val len = string.length
  if len == 1 then
    s"${string}  |"
  else if len == 2 then
    s"${string} |"
  else
    s"${string}|"

def matrixTable(): Unit =
  print("Matrix table: \n")
  for i <- -1 to 12 do
    val n = if (i == -1) " " else s"${i}"
    var line = if (n.length == 2) s"|${n} |" else s"|${n}  |"
    for j <- 0 to 12 do
      if i == -1 then
        line += box(s"${j}")
      else
        line += box(s"${i * j}")
    println(line)

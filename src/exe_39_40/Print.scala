package src.exe_39_40

def printTable(dataSet: List[Map[String, String]]): Unit =
  println("Name" + " " * 15 + "| Position" + " " * 10 + "| Separation date")
  println("-" * 19 + "|" + "-" * 19 + "|" + "-" * 16)
  for data <- dataSet do
    val sep1 = 19 - (data("First Name").length + data("Last Name").length + 1)
    val sep2 = 19 - (data("Position").length + 2)
    println(s"${data("First Name")} ${data("Last Name")}" +
      " " * sep1 + s"| ${data("Position")} " + " " * sep2 + s"| ${data("Separation date")}")
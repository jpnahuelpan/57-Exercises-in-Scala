package src.exe_47

def printThePeople(data: Data): Unit = 
  println(s"\nThere are ${data.number} people in espace right now:\n")
  println("Name" + " "*15 + "|" + " "*2 + "Craft")
  println("-"*19 + "|" + "-"*7)
  for person <- data.people do
    val space = 19 - (person("name").length)
    println(s"${person("name")}" + " "*space + "|" + s"  ${person("craft")}")
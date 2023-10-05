package src.exe_39_40

def validateInput(dataSet: List[Map[String, String]], column: String): Boolean =
  if dataSet(1).contains(column) then
    true
  else
    throw Exception("The entered value does not exist as a column in the table. Try again.\n")
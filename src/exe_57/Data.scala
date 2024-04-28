package src.exe_57

import scala.beans.BeanProperty
import java.util.{
  Arrays,
  List => javaList
}

class Question(
  @BeanProperty var question: String,
  @BeanProperty var answer: String,
  @BeanProperty var distraction: String):
  override def toString = s"Question(question=$question, answer=$answer, distraction=$distraction)"
  def this() = this("", "", "")

class Questions(@BeanProperty var questions: javaList[Question]):
  override def toString = s"Questions(questions=$questions)"
  def this() = this(Arrays.asList(Question("", "", "")))

//> using lib "org.http4s::http4s-ember-client:0.23.24"
//> using lib "org.http4s::http4s-circe:0.23.24"
//> using lib "io.circe::circe-generic:0.14.6"
//> using lib "org.scalafx:scalafx_3:21.0.0-R32"
//> using lib "org.slf4j:slf4j-nop:2.0.9"

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.{Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.control.{Label, TextField, Button, ScrollPane}
import scalafx.scene.layout.{VBox, HBox}
import scalafx.stage.Stage

import src.exe_49.{extractImagesUrls}

/** Flickr Photo Search
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program with a graphical interface that takes in a
  * search string and displays photographs that match that
  * search string.
  * 
  * Note:
  *   Execution comand should be:
  *     scala-cli exercise49.scala src/exe_49
  *
  *   The only purpose of slf4j-nop is to prevent client log messages from being printed in the prompt.
  *
  */
object exercise49 extends JFXApp3 {
  override def start(): Unit =
    val inputTextField1 = new TextField { 
      promptText="Enter a image topic"
    }
    val inputTextField2 = new TextField { 
      promptText="Images per row"
    }
    val resultLabel = new Label("")
    val submitButton = new Button("Search")
    
    val hbox = new HBox(inputTextField1, inputTextField2, submitButton, resultLabel)
    var vbox = new VBox(hbox)

    def update(): Unit =
      val imagesPerRow = inputTextField2.text().toInt
      val views = extractImagesUrls(inputTextField1.text()).map(imgData => new ImageView(url=imgData.imgUrl))
      vbox.children = hbox
      views.grouped(imagesPerRow).map(row => vbox.children.add(new HBox { children = row })).toList
      resultLabel.text = s"  ${views.length} photos about \"${inputTextField1.text()}\""
      inputTextField1.text = ""
    submitButton.onAction = _ => update()
    val scrollPane = new ScrollPane {
      content = vbox
      hbarPolicy = scalafx.scene.control.ScrollPane.ScrollBarPolicy.AsNeeded
      vbarPolicy = scalafx.scene.control.ScrollPane.ScrollBarPolicy.AsNeeded
    }
    stage = new JFXApp3.PrimaryStage {
      title = "Flickr Photo Search"
      scene = new Scene {
        root = scrollPane
      }
    }
}


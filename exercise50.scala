//> using lib "org.slf4j:slf4j-nop:2.0.9"
//> using lib "org.http4s::http4s-ember-client:0.23.24"
//> using lib "org.http4s::http4s-circe:0.23.24"
//> using lib "io.circe::circe-generic:0.14.6"
//> using lib "org.scalafx:scalafx_3:21.0.0-R32"
//> using lib "io.github.cdimascio:dotenv-java:3.0.0"

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.{Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.control.{Label, TextField, Button, ScrollPane}
import scalafx.scene.layout.{VBox, HBox}
import scalafx.stage.Stage
import scalafx.scene.text.{Text}

import io.github.cdimascio.dotenv.Dotenv
// local import
import src.exe_50.{getMovieData, createTextMovie}

/** Movie Recommendations
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Program that displays information about a given movie.
  * 
  * Note:
  *   Execution comand should be:
  *     scala-cli exercise50.scala src/exe_50
  *
  *   The only purpose of slf4j-nop is to prevent client log messages from being printed in the prompt.
  *
  *   Regarding the API Key, it should be requested by registering at https://www.omdbapi.com/.
  *   Then, create a file named '.env' in the root of the folder and inside it, add the following:
  *
  *   OMDB_API_KEY = "<your key>"
  *
  */
object exercise50 extends JFXApp3 {
  override def start(): Unit =
    val dotenv = Dotenv.load()
    val key = dotenv.get("OMBD_API_KEY")
    val inputTextField1 = new TextField {
      promptText="Enter the name of a movie"
    }
    val inputTextField2 = new TextField {
      promptText="Movies per row (dft=3)"
    }
    val resultLabel = new Label("")
    val submitButton = new Button("Search")

    val hbox = new HBox(inputTextField1, inputTextField2, submitButton, resultLabel)
    var vbox = new VBox(hbox)
    def update(): Unit =
      val imagesPerRow = if (inputTextField2.text() != "") inputTextField2.text().toInt else 3
      val moviesData = getMovieData(inputTextField1.text(), key)
      val views = moviesData.Search.map(movieData => new VBox(
        new ImageView(url=movieData.Poster),
        new Text(createTextMovie(movieData))
        ))
      vbox.children = hbox
      views.grouped(imagesPerRow).map(row => vbox.children.add(new HBox { children = row })).toList
      resultLabel.text = s"  ${views.length} movies about \"${inputTextField1.text()}\""
      inputTextField1.text = ""
    submitButton.onAction = _ => update()
    val scrollPane = new ScrollPane {
      content = vbox
      hbarPolicy = scalafx.scene.control.ScrollPane.ScrollBarPolicy.AsNeeded
      vbarPolicy = scalafx.scene.control.ScrollPane.ScrollBarPolicy.AsNeeded
    }
    stage = new JFXApp3.PrimaryStage {
      title = "Movie Recommendations"
      scene = new Scene {
        root = scrollPane
      }
    }
}

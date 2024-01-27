//> using lib "com.google.cloud:google-cloud-firestore:3.16.1"
//> using lib "com.google.api:api-common:2.24.0"
//> using lib "com.google.auth:google-auth-library-oauth2-http:1.22.0"

import com.google.auth.oauth2.GoogleCredentials
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.{
  DocumentReference,
  Firestore,
  FirestoreOptions,
  QuerySnapshot,
  QueryDocumentSnapshot,
  WriteResult
}

import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.{
  List => javaList,
  HashMap => javaHashMap,
  Map => javaMap
}


/** Pushing Notes to Firebase
  *
  * @author Juan Pablo Nahuelpán
  * 
  * Simple command-line application that lets you save
  * and show notes, using Firebase to save the notes.
  *
  * Note:
  *   Execution comand should be:
  *     scala-cli exercise51.scala -- <action> <note (if action=new)>
  *
  *   API Key Requirement:
  *   - Request an API key by registering at https://console.cloud.google.com/.
  *   - Generate a file named 'scalaexercise51.json' with your credentials and download it to the root folder.
  *
  *   Library and Guides:
  *   - The script utilizes the server client library for Java.
  *   - Refer to the Java client library overview (https://cloud.google.com/java/docs/reference/google-cloud-firestore/latest/overview) and
  *     the basic Java guide (https://cloud.google.com/firestore/docs/create-database-server-client-library?hl=es-419#firestore_setup_dataset_pt1-java).
  *
  */


@main def exercise51(action: String, note: String*): Unit =
  if List("new", "show").contains(action.toLowerCase) then
    val db: Firestore = getDB("scalaexercise51.json", "scalaexercise51")
    if action.toLowerCase == "new" then
      val docData: javaMap[String, String] = javaHashMap()
      docData.put("note", note.mkString(" "))
      val future: ApiFuture[WriteResult] = db.collection("notes").document().set(docData)
      println(s"${future.get()}")
      println("Your note was saved.")
    else
      val query: ApiFuture[QuerySnapshot] = db.collection("notes").get()
      val querySnapshot: QuerySnapshot = query.get()
      val documents: javaList[QueryDocumentSnapshot] = querySnapshot.getDocuments()
      documents.forEach(doc =>
        val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.format(doc.getCreateTime().toDate())
        println(s"${date} - ${doc.getString("note")}")
      )
  else
    println("The action don't exist.")


/** this function inicializate conecction to the firestore DDBB */
def getDB(credentialsFile: String, projectId: String): Firestore =
  val credentials: GoogleCredentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFile))
  val firestoreOptions: FirestoreOptions =
    FirestoreOptions.getDefaultInstance().toBuilder()
      .setProjectId(projectId)
      .setCredentials(credentials)
      .build()
  firestoreOptions.getService()


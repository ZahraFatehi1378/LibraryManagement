package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import java.net.URL
import java.util.*

class ReturnBook:Initializable {
    @FXML
    var commit : Button?=null

    @FXML
    var book_id: TextField?=null

    @FXML
    fun commit(event: MouseEvent){
        var libraryConnection= LibraryConnection()
        libraryConnection.returnBook( book_id?.text.toString())
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }
}
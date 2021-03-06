package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import java.net.URL
import java.util.*

class BorrowBook:Initializable {

    @FXML
    var commit :Button?=null

    @FXML
    var book_issue:TextField?=null

    @FXML
    var book_id:TextField?=null

    @FXML
    fun commit(event: MouseEvent){
        var libraryConnection= LibraryConnection()
        var result =libraryConnection.borrow( book_id?.text.toString(), book_issue?.text.toString())
        var alert = Alert(Alert.AlertType.INFORMATION)
        alert.contentText = result
        alert.show()

    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }


}
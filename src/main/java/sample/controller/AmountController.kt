package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import sample.controller.LoginController.Companion.token

class AmountController {

    @FXML
    var commit :Button?=null
    @FXML
    var amount : TextField?=null

    fun commit(event: MouseEvent){

        var libraryConnection = LibraryConnection()
        var result =libraryConnection.incraese( amount?.text.toString())
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.contentText = result
        alert.title = "you can not enter"
        alert.show()
    }
}
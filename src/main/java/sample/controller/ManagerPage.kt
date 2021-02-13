package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import sample.models.PersonAllInfo
import java.io.IOException
import java.net.URL
import java.util.*

class ManagerPage : LibrarianPage() {

    @FXML
    var delete_user: Button? = null

}
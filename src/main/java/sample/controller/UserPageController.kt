package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.util.*

class UserPageController : Initializable {

    @FXML
    var information: Button? = null

    @FXML
    var borrow_book: Button? = null

    @FXML
    var return_book: Button? = null

    @FXML
    var search_book: Button? = null

    @FXML
    var increase_balance: Button? = null

    @FXML
    fun getInfo(event: MouseEvent) {


        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/showInfo.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()

    }

    @FXML
    fun borrow(event: MouseEvent) {
        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/borrow.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()
    }

    @FXML
    fun returnBook(event: MouseEvent) {
        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/return.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()
    }

    @FXML
    fun searchBook(event: MouseEvent) {
        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/search.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()
    }

    @FXML
    fun increaseBalance(event: MouseEvent) {
        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/add_amount.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()

    }

    @FXML
    fun logout(event: MouseEvent) {
        var libraryConnection = LibraryConnection()
        var result=libraryConnection.logOut();

        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.contentText = result
        alert.title = "you can not enter"
        alert.show()

        var stage: Stage? = information?.scene?.window as Stage?
        stage?.close()
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }


}
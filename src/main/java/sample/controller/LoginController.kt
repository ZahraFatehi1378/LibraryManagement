package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import javafx.scene.Scene
import java.io.IOException
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.control.Alert
import java.net.URL
import java.util.*


class LoginController : Initializable {
    var libraryConnection = LibraryConnection()

    companion object {
        @JvmStatic
        var token: String? = null
    }

    @FXML
    var login: Button? = null

    @FXML
    var signup: Button? = null

    @FXML
    var username: TextField? = null

    @FXML
    var password: TextField? = null


    @FXML
    fun loginListener(event: MouseEvent) {
        var result =
            libraryConnection?.executeLogin(username = username?.text.toString(), password = password?.text.toString())
        val primaryStage = Stage()
        var root: Parent? = null

        if (result != "you need to sign up") {
            token = result
            var stage: Stage? = login?.scene?.window as Stage?
            stage?.close()
            if (libraryConnection.getInfo(token , "" ,"")?.get(0)?.state == "manager") {
                try {
                    root = FXMLLoader.load(javaClass.getResource("/fxml/manager_page.fxml"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                primaryStage.title = "signup"
                primaryStage.scene = Scene(root, 1000.0, 700.0)
                primaryStage.show()
            } else if (libraryConnection.getInfo(token, "" ,"")?.get(0)?.state == "librarian") {
                try {
                    root = FXMLLoader.load(javaClass.getResource("/fxml/librarian_page.fxml"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                primaryStage.title = "signup"
                primaryStage.scene = Scene(root, 1000.0, 700.0)
                primaryStage.show()

            } else {
                try {
                    root = FXMLLoader.load(javaClass.getResource("/fxml/user_page.fxml"))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                primaryStage.title = "signup"
                primaryStage.scene = Scene(root, 1000.0, 700.0)
                primaryStage.show()
            }

        } else {

            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.contentText = result
            alert.title = "you can not enter"
            alert.show()
        }
    }

    @FXML
    fun signupListener(event: MouseEvent?) {

        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/signup.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.title = "signup"
        primaryStage.scene = Scene(root, 700.0, 500.0)
        primaryStage.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {}
}


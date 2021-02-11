package sample

import connections.LibraryConnection
import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import sample.controller.LoginController
import java.lang.Exception

fun main() {
    launch(Main::class.java)

}

class Main : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val library = LibraryConnection()
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/fxml/login.fxml"))
        primaryStage.title = "Library"
        primaryStage.scene = Scene(root, 600.0, 400.0)
        primaryStage.show()
    }

}

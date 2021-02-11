package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import sample.models.PersonAllInfo
import java.io.IOException
import java.net.URL
import java.util.*

class ManagerPage : Initializable {
    override fun initialize(location: URL?, resources: ResourceBundle?) {}

    @FXML
    var information: Button? = null

    @FXML
    var add_book: Button? = null

    @FXML
    var delete_user: Button? = null

    @FXML
    var search_member_pass: Button? = null

    @FXML
    var search_member_username: Button? = null

    @FXML
    var inbox: Button? = null

    @FXML
    var author_name: TextField? = null

    @FXML
    var title: TextField? = null

    @FXML
    var publisher_id: TextField? = null

    @FXML
    var publish_year: TextField? = null

    @FXML
    var category: TextField? = null
    @FXML
    var book_number: TextField? = null

    @FXML
    var pages: TextField? = null

    @FXML
    var price: TextField? = null

    @FXML
    var username: TextField? = null

    @FXML
    var search_with: TextField? = null

    @FXML
    var txt_area: TextArea? = null

    var libraryConnection = LibraryConnection()

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
    fun addBook(event: MouseEvent) {
        libraryConnection.addBook(
            author_name?.text.toString() , title?.text.toString() ,
            publisher_id?.text.toString() , price?.text.toString() , pages?.text.toString() ,
            book_number?.text.toString() , category?.text.toString()
            , publish_year?.text.toString() )
    }

    @FXML
    fun deleteUser(event: MouseEvent) {
        libraryConnection.deleteUser(username?.text.toString())
    }

    @FXML
    fun searchMember_pass(event: MouseEvent) {
        txt_area?.text  = setTxt(libraryConnection.getInfo("" ,search_with?.text.toString() , ""))
    }

    private fun setTxt(info: List<PersonAllInfo>?): String {
        var txt ="username   password   firstname    lastname    accountBalance    nationalId    crate date    state    address   phone\n"
        if (info != null) {
            for (a in info){
                txt+="${a.username}   ${a.password}   ${a.name_first_name}   ${a.name_last_name}   ${a.account_balance}" +
                        "  ${a.national_id}   ${a.create_date}   ${a.state}   ${a.address}   ${a.phone_number}\n"
            }
        }
        return txt
    }

    @FXML
    fun searchMember_username(event: MouseEvent) {
        txt_area?.text  = setTxt(libraryConnection.getInfo("" , "",search_with?.text.toString()))

    }

    @FXML
    fun inbox(event: MouseEvent) {
        var allTxt = libraryConnection.getInbox()
        var txt =""
        for (a in allTxt){
            txt+="${a}\n"
        }
        txt_area?.text  = txt
    }

    @FXML
    fun logout(event: MouseEvent) {
        var libraryConnection = LibraryConnection()
        var result = libraryConnection.logOut();

        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.contentText = result
        alert.title = "you can not enter"
        alert.show()

        var stage: Stage? = information?.scene?.window as Stage?
        stage?.close()
    }


}
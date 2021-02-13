package sample.controller

import connections.LibraryConnection
import javafx.concurrent.Task
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import sample.models.BookModel
import sample.models.PersonAllInfo
import java.io.IOException
import java.net.URL
import java.util.*

open class LibrarianPage : Initializable {

    @FXML
    var information: Button? = null

    @FXML
    var add_book: Button? = null

    @FXML
    var search_member_lastname: Button? = null

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
    var table: TableView<PersonAllInfo>? = null

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
        var result =libraryConnection.deleteUser(username?.text.toString())

        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.contentText = result
        alert.title = "you can not enter"
        alert.show()
    }

    @FXML
    fun searchMember_lastname(event: MouseEvent) {

        createTable(libraryConnection.getInfo("" , "",search_with?.text.toString()))
    }

    @FXML
    fun searchMember_username(event: MouseEvent) {

        createTable(libraryConnection.getInfo("" ,search_with?.text.toString() , ""))
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {

        table!!.columns!!.add(newClmn("Username","username"));
        table!!.columns!!.add(newClmn("Password","password"));
        table!!.columns!!.add(newClmn("First Name","name_first_name"));
        table!!.columns!!.add(newClmn("Last Name","name_last_name"));
        table!!.columns!!.add(newClmn("Account Balance","account_balance"));
        table!!.columns!!.add(newClmn("National ID","national_id"));
        table!!.columns!!.add(newClmn("Create Date","create_date"));
        table!!.columns!!.add(newClmn("State","state"));
        table!!.columns!!.add(newClmn("Address","address"));
        table!!.columns!!.add(newClmn("Phone Number","phone_number"));
    }

    private fun createTable(info: List<PersonAllInfo>?) {
        table!!.items.clear()
        if (info != null) {
            for (a in info){
                println(a);
                table!!.items.add(a);
            }
        }
    }

    private fun newClmn(name: String,field: String) : TableColumn<PersonAllInfo,String> {
        val clmn = TableColumn<PersonAllInfo,String>(name)
        clmn.cellValueFactory = PropertyValueFactory(field)
        return clmn
    }

    @FXML
    fun inbox(event: MouseEvent) {

        val primaryStage = Stage()
        var root: Parent? = null
        try {
            root = FXMLLoader.load(javaClass.getResource("/fxml/inbox.fxml"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        primaryStage.scene = Scene(root, 1000.0, 700.0)
        primaryStage.show()

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
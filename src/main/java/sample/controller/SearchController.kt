package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import sample.models.BookModel
import sample.models.PersonAllInfo
import java.net.URL
import java.util.*

class SearchController:Initializable {

    @FXML
    var author: TextField? = null

    @FXML
    var title: TextField? = null

    @FXML
    var published_year: TextField? = null

    @FXML
    var book_num: TextField? = null


    @FXML
    var table: TableView<BookModel>? = null


    @FXML
    fun search(event: MouseEvent){
        var libraryConnection = LibraryConnection()

        createTable( libraryConnection.search(author?.text.toString() , title?.text.toString() ,
            published_year?.text.toString() , book_num?.text.toString() ))
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        table!!.columns!!.add(newClmn("book number","book_number"));
        table!!.columns!!.add(newClmn("title","title"));
        table!!.columns!!.add(newClmn("category","category"));
        table!!.columns!!.add(newClmn("pages","pages"));
        table!!.columns!!.add(newClmn("publisher id","publisher_id"));
        table!!.columns!!.add(newClmn("price","price"));
        table!!.columns!!.add(newClmn("published year","published_year"));
        table!!.columns!!.add(newClmn("State","available"));
        table!!.columns!!.add(newClmn("author id","author_id"));
        table!!.columns!!.add(newClmn("name","name"));
    }

    private fun createTable(info: List<BookModel>?) {
        table!!.items.clear()
        if (info != null) {
            for (a in info){
                println(a);
                table!!.items.add(a);
            }
        }
    }

    private fun newClmn(name: String,field: String) : TableColumn<BookModel,String> {
        val clmn = TableColumn<BookModel,String>(name)
        clmn.cellValueFactory = PropertyValueFactory(field)
        return clmn
    }
}
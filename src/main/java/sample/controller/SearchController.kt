package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent

class SearchController {

    @FXML
    var author: TextField? = null

    @FXML
    var title: TextField? = null

    @FXML
    var published_year: TextField? = null

    @FXML
    var book_num: TextField? = null

    @FXML
    var category: TextField? = null

    @FXML
    var search_result:TextArea?=null

    @FXML
    fun search(event: MouseEvent){
        var libraryConnection = LibraryConnection()
        libraryConnection.search(author?.text.toString() , title?.text.toString() ,
            published_year?.text.toString() , book_num?.text.toString() , category?.text.toString() )
    }
}
package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import sample.models.PersonAllInfo
import sample.models.TxtModel
import java.net.URL
import java.util.*

class InboxController :Initializable{

    @FXML
    var table : TableView<TxtModel>? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        var libraryConnection =LibraryConnection()
        table!!.columns!!.add(newClmn("massages","txt"))

        createTable(libraryConnection.getInbox())

    }

    private fun createTable(info: List<TxtModel>?) {
        table!!.items.clear()
        if (info != null) {
            for (a in info){
                println(a);
                table!!.items.add(a);
            }
        }
    }

    private fun newClmn(name: String,field: String) : TableColumn<TxtModel, String> {
        val clmn = TableColumn<TxtModel,String>(name)
        clmn.cellValueFactory = PropertyValueFactory(field)
        return clmn
    }
}
package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextArea
import java.net.URL
import java.util.*

class ShowInfoController :Initializable{
    @FXML
    var username: TextArea? = null

    @FXML
    var password: TextArea? = null

    @FXML
    var created_date: TextArea? = null

    @FXML
    var firstname: TextArea? = null

    @FXML
    var account_balance: TextArea? = null

    @FXML
    var address1: TextArea? = null

    @FXML
    var address2: TextArea? = null

    @FXML
    var national_id: TextArea? = null

    @FXML
    var state_info: TextArea? = null

    @FXML
    var phone1: TextArea? = null

    @FXML
    var phone2: TextArea? = null

    @FXML
    var lastname: TextArea? = null

    fun setInfo(){
        var libraryConnection = LibraryConnection()
        var list = libraryConnection.getInfo(LoginController.token ,"" ,"")


        username?.text = list?.get(0)?.username
        password?.text = list?.get(0)?.password
        firstname?.text = list?.get(0)?.name_first_name
        lastname?.text = list?.get(0)?.name_last_name
        phone1?.text = list?.get(0)?.phone_number
        phone2?.text = list?.get(1)?.phone_number
        address1?.text = list?.get(0)?.address
        address2?.text = list?.get(1)?.address
        account_balance?.text = list?.get(0)?.account_balance
        state_info?.text = "${list?.get(0)?.state } : ${list?.get(0)?.student_id } _ ${list?.get(0)?.univercity_name }"
        created_date?.text = list?.get(0)?.create_date
        national_id?.text = list?.get(0)?.national_id

    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setInfo()
    }
}
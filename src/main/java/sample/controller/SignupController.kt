package sample.controller

import connections.LibraryConnection
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import java.net.URL
import java.util.*

class SignupController :Initializable{


    @FXML
    var commitBtn: Button? = null
    @FXML
    var username: TextField? = null
    @FXML
    var password: TextField? = null
    @FXML
    var national_id: TextField? = null
    @FXML
    var firstname: TextField? = null
    @FXML
    var lastname: TextField? = null
    @FXML
    var student_instructor_number: TextField? = null
    @FXML
    var uni_job: TextField? = null
    @FXML
    var phone_number1: TextField? = null
    @FXML
    var phone_number2: TextField? = null
    @FXML
    var address1: TextField? = null
    @FXML
    var address2: TextField? = null
    @FXML
    var state: TextField? = null



    @FXML
    fun commit(event: MouseEvent?){

        var libraryConnection = LibraryConnection()
        var  result =libraryConnection.executeSignUp( username = username?.text.toString() , password =  password?.text.toString()
            ,national_id = national_id?.text.toString(),firstname = firstname?.text.toString() , lastname = lastname?.text.toString(),
        student_instructor_id = student_instructor_number?.text.toString() , university_job = uni_job?.text.toString() ,user_state = state?.text.toString(),
        phone1 =  phone_number1?.text.toString(), phone2 = phone_number2?.text.toString() , address1 = address1?.text.toString() , address2 = address2?.text.toString())

        var a = Alert(Alert.AlertType.INFORMATION)
        a.contentText = result
        a.show()

//        var stage: Stage? = commitBtn?.scene?.window as Stage?
//        stage?.close()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }
}
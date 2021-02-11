package connections

import javafx.fxml.FXML
import sample.controller.LoginController.Companion.token
import sample.models.PersonAllInfo
import sample.models.TxtModel
import java.sql.*
import java.util.ArrayList

class LibraryConnection {


    lateinit var conn: Connection

    // JDBC driver name and database URL
    val JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"
    val DB_URL = "jdbc:mysql://localhost:3306/library_db"

    //  Database credentials
    val USER = "root"
    val PASS = ""


    fun getConnection() {
        try {
            Class.forName(JDBC_DRIVER).newInstance()
            conn = DriverManager.getConnection(DB_URL, USER, PASS)
            print("is connecting ...")
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    fun executeLogin(username: String, password: String): String {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var token = ""

        try {
            stmt = conn!!.createStatement()
            resultset = stmt.executeQuery("call isAccountValid('${username}' , '${password}' );")

            while (resultset!!.next()) {
                token = resultset.getString("token")
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
            }
        }
        return token
    }


    fun executeSignUp(
        username: String,
        password: String,
        national_id: String,
        firstname: String,
        lastname: String,
        student_instructor_id: String,
        university_job: String,
        user_state: String,
        phone1: String,
        phone2: String,
        address1: String,
        address2: String
    ) {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            stmt!!.executeUpdate(
                "call create_account('${username}' , '${password}' , ${national_id} " +
                        ", '${firstname}' , '${lastname}' ,${student_instructor_id} , '${university_job}' , '${user_state}'," +
                        "'${phone1}','${phone2}','${address1}','${address2}');"
            )
            //         stmt!!.executeUpdate("call create_account('fateeeehi' , 'sefeeewfewf52' , 76454238 , 'mmdf' , 'fatesgh' ,32453 , 'amirkabir' , 'student' , 12324434 , 123134,'ffre','rref');")

//            while (resultset!!.next()) {
//             //   println(resultset.getString("Database"))
//            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }

    fun getInfo(token: String?, searchWithUsername: String, searchWithLastname: String): List<PersonAllInfo>? {
        var state = getPersonState(token)
        if (state == "") {
            return null
        }
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        val list: MutableList<PersonAllInfo> = ArrayList()

        try {
            stmt = conn.createStatement()

            if (token != "")
                resultset = stmt!!.executeQuery("call getAllInfo('${token}');")

            if (searchWithUsername != "")
                resultset = stmt!!.executeQuery("call search_users_personal_info_username('${searchWithUsername}');")

            if (searchWithLastname != "")
                resultset = stmt!!.executeQuery("call search_users_personal_info_lastname('${searchWithLastname}');")

            when (state) {
                "student" -> {
                    while (resultset!!.next()) {
                        val personAllInfo = PersonAllInfo(
                            resultset.getString("username"),
                            resultset.getString("password"),
                            resultset.getString("account_balance"),
                            resultset.getString("create_date"),
                            resultset.getString("national_id"),
                            resultset.getString("name_first_name"),
                            resultset.getString("name_last_name"),
                            resultset.getString("student_id"),
                            resultset.getString("univercity_name"),
                            resultset.getString("phone_number"),
                            resultset.getString("address"),
                            "student"
                        )
                        list.add(personAllInfo)
                    }
                }
                "instructor" -> {
                    while (resultset!!.next()) {
                        val personAllInfo = PersonAllInfo(
                            resultset.getString("username"),
                            resultset.getString("password"),
                            resultset.getString("account_balance"),
                            resultset.getString("create_date"),
                            resultset.getString("national_id"),
                            resultset.getString("name_first_name"),
                            resultset.getString("name_last_name"),
                            resultset.getString("instructor_id"),
                            resultset.getString("univercity_name"),
                            resultset.getString("phone_number"),
                            resultset.getString("address"),
                            "instructor"
                        )
                        list.add(personAllInfo)
                    }
                }
                "regular" -> {
                    while (resultset!!.next()) {
                        val personAllInfo = PersonAllInfo(
                            resultset.getString("username"),
                            resultset.getString("password"),
                            resultset.getString("account_balance"),
                            resultset.getString("create_date"),
                            resultset.getString("national_id"),
                            resultset.getString("name_first_name"),
                            resultset.getString("name_last_name"),
                            "",
                            resultset.getString("job"),
                            resultset.getString("phone_number"),
                            resultset.getString("address"),
                            "regular"
                        )
                        list.add(personAllInfo)
                    }
                }
                "librarian" -> {
                    while (resultset!!.next()) {
                        val personAllInfo = PersonAllInfo(
                            resultset.getString("username"),
                            resultset.getString("password"),
                            resultset.getString("account_balance"),
                            resultset.getString("create_date"),
                            resultset.getString("national_id"),
                            resultset.getString("name_first_name"),
                            resultset.getString("name_last_name"),
                            "",
                            resultset.getString("state"),
                            resultset.getString("phone_number"),
                            resultset.getString("address"),
                            "librarian"
                        )
                        list.add(personAllInfo)
                    }
                }
                "manager" -> {
                    while (resultset!!.next()) {
                        val personAllInfo = PersonAllInfo(
                            resultset.getString("username"),
                            resultset.getString("password"),
                            resultset.getString("account_balance"),
                            resultset.getString("create_date"),
                            resultset.getString("national_id"),
                            resultset.getString("name_first_name"),
                            resultset.getString("name_last_name"),
                            "",
                            resultset.getString("state"),
                            resultset.getString("phone_number"),
                            resultset.getString("address"),
                            "manager"
                        )
                        list.add(personAllInfo)
                    }
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
        return list
    }


    fun getPersonState(token: String?): String {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset = stmt!!.executeQuery("call get_person_state('${token}');")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("person_state")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
        return state
    }

    fun incraese(amount: String?): String {
        println(amount)
        print(token)

        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset = stmt!!.executeQuery("call incease_account_balance('${token}', ${amount});")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("result")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
        return state
    }

    fun search(author: String, title: String, published_year: String, book_num: String, category: String) {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset =
                stmt!!.executeQuery("call search_book('${title}', '${author}' ,'${book_num}', '${published_year}' ,'${category}');")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("person_state")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }

    fun borrow(bookId: String, bookIssue: String) {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset = stmt!!.executeQuery("call getBookP('${token}',${bookId}, ${bookIssue});")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("person_state")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }

    fun returnBook(bookId: String) {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset = stmt!!.executeQuery("call returnBookP('${token}',${bookId});")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("person_state")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }

    fun logOut(): String {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            resultset = stmt!!.executeQuery("call signout('${token}');")
            val list: MutableList<PersonAllInfo> = ArrayList()

            while (resultset!!.next()) {
                state = resultset.getString("state")

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
        return state
    }

    fun getInbox(): List<String> {

        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        val list: MutableList<String> = ArrayList()

        try {
            stmt = conn.createStatement()

            resultset = stmt!!.executeQuery("call get_inbox_info();")

            while (resultset!!.next()) {
                list.add(resultset.getString("txt"))

            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
        return list
    }

    fun deleteUser(username: String?) {
        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            stmt!!.executeUpdate("call delete_user('${username}');")

        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }

    fun addBook(
        author_name: String, title: String,
        publisher_id: String, price: String, pages: String,
        book_number: String, category: String, publish_year: String
    ) {

        getConnection()
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        var state = ""

        try {
            stmt = conn.createStatement()
            stmt!!.executeUpdate("call add_book(${book_number},'${title}','${category}',${pages},${publisher_id} ,${price} , '${author_name}'  , ${publish_year});")

        } catch (ex: SQLException) {
            ex.printStackTrace()
        } finally {
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }
                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }
                stmt = null
            }

        }
    }
}

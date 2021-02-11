module LibraryManagement {
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;

   opens sample.controller to javafx.fxml;
   opens sample;
}
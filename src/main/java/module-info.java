module com.example.gym {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
     requires java.sql;
    requires jbcrypt;


    opens com.example.gym to javafx.fxml;
    exports com.example.gym;
}
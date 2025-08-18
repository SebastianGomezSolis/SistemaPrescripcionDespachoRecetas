module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires jdk.compiler;


    opens controllers to javafx.fxml;
    exports controllers;
}
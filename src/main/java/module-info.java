module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires jdk.compiler;


    opens com.sistema.sistemaprescripciondespachorecetas.controller to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas.controller;
    exports com.sistema.sistemaprescripciondespachorecetas.view;
    opens com.sistema.sistemaprescripciondespachorecetas.view to javafx.fxml;
}
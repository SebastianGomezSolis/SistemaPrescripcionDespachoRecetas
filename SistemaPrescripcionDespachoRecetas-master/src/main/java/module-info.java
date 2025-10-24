module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires java.logging;
    requires javafx.graphics;


    // Opens para JavaFX
    opens com.sistema.sistemaprescripciondespachorecetas to javafx.fxml;
    opens com.sistema.sistemaprescripciondespachorecetas.controller to javafx.fxml;
    opens com.sistema.sistemaprescripciondespachorecetas.model to javafx.fxml;


    // Exports
    exports com.sistema.sistemaprescripciondespachorecetas;
    exports com.sistema.sistemaprescripciondespachorecetas.controller;
    exports com.sistema.sistemaprescripciondespachorecetas.model;
    exports com.sistema.sistemaprescripciondespachorecetas.datos;
    exports com.sistema.sistemaprescripciondespachorecetas.logic;
}
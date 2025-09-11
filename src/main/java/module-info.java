module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.xml.bind;

    // Opens para JavaFX
    opens com.sistema.sistemaprescripciondespachorecetas to javafx.fxml;
    opens com.sistema.sistemaprescripciondespachorecetas.controller to javafx.fxml;
    opens com.sistema.sistemaprescripciondespachorecetas.model to javafx.fxml;

    // Opens para JAXB - ESTAS LÍNEAS ESTÁN FALTANDO
    opens com.sistema.sistemaprescripciondespachorecetas.datos.entity to jakarta.xml.bind;
    opens com.sistema.sistemaprescripciondespachorecetas.datos.conector to jakarta.xml.bind;
    opens com.sistema.sistemaprescripciondespachorecetas.datos.dato to jakarta.xml.bind;

    // Exports
    exports com.sistema.sistemaprescripciondespachorecetas;
    exports com.sistema.sistemaprescripciondespachorecetas.controller;
    exports com.sistema.sistemaprescripciondespachorecetas.model;
    exports com.sistema.sistemaprescripciondespachorecetas.datos;
    exports com.sistema.sistemaprescripciondespachorecetas.datos.entity;
    exports com.sistema.sistemaprescripciondespachorecetas.datos.conector;
    exports com.sistema.sistemaprescripciondespachorecetas.logic.logica;
    opens com.sistema.sistemaprescripciondespachorecetas.logic.logica to jakarta.xml.bind;
}
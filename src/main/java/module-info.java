module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires javafx.graphics;
    requires com.sistema.sistemaprescripciondespachorecetas;

    opens com.sistema.sistemaprescripciondespachorecetas.controller to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas.controller;

    opens com.sistema.sistemaprescripciondespachorecetas to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas;

    opens com.sistema.sistemaprescripciondespachorecetas.datos to jakarta.xml.bind;
    exports com.sistema.sistemaprescripciondespachorecetas.datos;
}

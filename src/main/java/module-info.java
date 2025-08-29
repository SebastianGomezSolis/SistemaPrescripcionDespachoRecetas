module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sistema.sistemaprescripciondespachorecetas.controller to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas.controller;
}

module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sistema.sistemaprescripciondespachorecetas to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas;
}
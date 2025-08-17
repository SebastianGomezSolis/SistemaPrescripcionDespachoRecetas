module com.sistema.sistemaprescripciondespachorecetas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.sistema.sistemaprescripciondespachorecetas;


    opens com.sistema.sistemaprescripciondespachorecetas to javafx.fxml;
    exports com.sistema.sistemaprescripciondespachorecetas;
}
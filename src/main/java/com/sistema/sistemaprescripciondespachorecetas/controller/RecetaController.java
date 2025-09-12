package com.sistema.sistemaprescripciondespachorecetas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.RecetaLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import javafx.stage.Stage;

public class RecetaController {

    @FXML private Spinner <Integer>spp_Cantidad;
    @FXML private Spinner <Integer> spp_duracion;
    @FXML private TextField txt_IndicacionesMedicamentos;

    private Receta receta;

    private static final String RUTA_RECETA= java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "recetas.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_Medicamentos controller = " + RUTA_RECETA);
    }

    private final RecetaLogica recetaLogica = new RecetaLogica(RUTA_RECETA);
    private boolean modoEdicion = false;

    @FXML
    public void initialize() {
        spp_Cantidad.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );
        spp_duracion.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1)
        );
    }
    public void setCliente(Receta receta, boolean editar) {
        this.receta = receta;
        this.modoEdicion = editar;

        if (editar && receta != null) {
            spp_Cantidad.getValueFactory().setValue(receta.getCantidad());
            spp_duracion.getValueFactory().setValue(receta.getDiasDuracion());
            txt_IndicacionesMedicamentos.setText(receta.getIndicaciones());
        }
    }
    @FXML
    public void GuardarMedicamento() {
        try{
            int cantidad = spp_Cantidad.getValue();
            int duracion = spp_duracion.getValue();
            String indicaciones = txt_IndicacionesMedicamentos.getText().trim();

            if (cantidad <=0 || duracion <=0 || indicaciones.isEmpty()) {
                mostrarAlerta("Campos incompletos", "Por favor, complete todos los campos del formulario.");
                return;
            }
            if (!modoEdicion) {
                receta = new Receta();
                recetaLogica.create(receta); // aun no implementado
            } else {
                // Editar receta existente
                receta.setCantidad(cantidad);
                receta.setDiasDuracion(duracion);
                receta.setIndicaciones(indicaciones);
            }

            Stage stage = (Stage) spp_Cantidad.getScene().getWindow();
            stage.setUserData(receta);
            stage.close();

        }
        catch (Exception error){
            mostrarAlerta("Error al guardar los datos", error.getMessage());
        }
    }

    @FXML
    public void CancelarMedicamento() {
        try
        {
            Stage stage = (Stage) spp_Cantidad.getScene().getWindow();
            stage.setUserData(null);
            stage.close();
        }
        catch (Exception error) {
            mostrarAlerta("Error al guardar los datos", error.getMessage());
        }

    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

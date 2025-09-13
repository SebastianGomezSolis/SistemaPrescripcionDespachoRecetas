package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.RecetaLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.RecetaDetalle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;

public class RecetaController {

    @FXML private Spinner<Integer> spp_Cantidad;
    @FXML private Spinner<Integer> spp_duracion;
    @FXML private TextField txt_IndicacionesMedicamentos;

    private static final String RUTA_RECETA = Paths.get(
            System.getProperty("user.dir"), "bd", "recetas.xml").toString();
    private final RecetaLogica recetaLogica = new RecetaLogica(RUTA_RECETA);

    private Receta recetaActual;
    private RecetaDetalle recetaDetalleSeleccionado;
    private Paciente pacienteSeleccionado;
    private Medicamento medicamentoSeleccionado;
    private Boolean modoEdicion = false;

    @FXML
    public void initialize() {
        spp_Cantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        spp_duracion.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1));
    }

    public void setRecetaActual(Receta receta) {
        this.recetaActual = receta;
        if (recetaActual.getMedicamentos() == null) {
            recetaActual.setMedicamentos(new ArrayList<>());
        }
    }

    public void setReceta(RecetaDetalle detalle, boolean editar) {
        this.recetaDetalleSeleccionado = detalle;
        this.modoEdicion = editar;

        if (editar && detalle != null) {
            this.medicamentoSeleccionado = detalle.getMedicamento();

            spp_Cantidad.getValueFactory().setValue(detalle.getCantidad());
            spp_duracion.getValueFactory().setValue(detalle.getDiasDuracion());
            txt_IndicacionesMedicamentos.setText(detalle.getIndicaciones());
        }
    }


    public void setPacienteSeleccionado(Paciente paciente) {
        this.pacienteSeleccionado = paciente;
    }

    public void setMedicamentoSeleccionado(Medicamento medicamento) {
        this.medicamentoSeleccionado = medicamento;
    }

    @FXML
    public void GuardarMedicamento() {
        try {

            if (medicamentoSeleccionado == null || recetaActual == null) {
                mostrarAlerta("Error", "Faltan datos para guardar el medicamento.");
                return;
            }
            if (modoEdicion && recetaDetalleSeleccionado != null) {
                recetaDetalleSeleccionado.setCantidad(spp_Cantidad.getValue());
                recetaDetalleSeleccionado.setDiasDuracion(spp_duracion.getValue());
                recetaDetalleSeleccionado.setIndicaciones(txt_IndicacionesMedicamentos.getText());
            } else {
                RecetaDetalle detalle = new RecetaDetalle();
                detalle.setMedicamento(medicamentoSeleccionado);
                detalle.setCantidad(spp_Cantidad.getValue());
                detalle.setDiasDuracion(spp_duracion.getValue());
                detalle.setIndicaciones(txt_IndicacionesMedicamentos.getText());
                recetaActual.getMedicamentos().add(detalle);
            }

            Stage stage = (Stage) spp_Cantidad.getScene().getWindow();
            stage.setUserData(recetaActual);
            stage.close();
        }
        catch (Exception e) {
            mostrarAlerta("Error al guardar los datos", e.getMessage());
        }
    }


    @FXML
    public void CancelarMedicamento() {
        Stage stage = (Stage) spp_Cantidad.getScene().getWindow();
        stage.setUserData(null);
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}

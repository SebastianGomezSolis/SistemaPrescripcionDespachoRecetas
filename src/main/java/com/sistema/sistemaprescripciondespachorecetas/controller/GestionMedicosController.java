package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GestionMedicosController {

    // Tabla y columnas
    @FXML private TableView<Medico> tablaMedicos;
    @FXML private TableColumn<Medico, String> colIdMedico;
    @FXML private TableColumn<Medico, String> colNombreMedico;
    @FXML private TableColumn<Medico, String> colEspecialidadMedico;

    // Campos de texto
    @FXML private TextField txtIdMedico;
    @FXML private TextField txtNombreMedico;
    @FXML private TextField txtEspecialidadMedico;
    @FXML private TextField txtBuscarMedico;

    private ObservableList<Medico> listaMedicos;
    private MedicoLogica medicoLogica;

    @FXML
    public void initialize() {
        medicoLogica = new MedicoLogica();

        colIdMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecialidadMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));

        listaMedicos = FXCollections.observableArrayList();

        refrescarTabla();
    }

    @FXML
    private void agregarMedico() {
        String id = txtIdMedico.getText().trim();
        String nombre = txtNombreMedico.getText().trim();
        String especialidad = txtEspecialidadMedico.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        Medico existente = medicoLogica.findById(id);

        if (existente != null) {

            try {
                medicoLogica.update(new Medico(id, id, nombre, especialidad));
                mostrarAlerta("Éxito", "Médico modificado.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {

            try {
                medicoLogica.create(new Medico(id, id, nombre, especialidad));
                mostrarAlerta("Éxito", "Médico agregado.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }

        refrescarTabla();
        limpiarCampos();
    }

    @FXML
    private void eliminarMedico() {
        String id = txtIdMedico.getText().trim();
        if (id.isEmpty()) {
            mostrarAlerta("Error", "Debe indicar un ID.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setHeaderText("Eliminar médico");
            confirm.setContentText("¿Está seguro que desea eliminar al médico con ID " + id + "?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                medicoLogica.deleteById(id);
                refrescarTabla();
                limpiarCampos();
                mostrarAlerta("Éxito", "Médico eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarMedico() {
        String id = txtBuscarMedico.getText().trim();
        if (id.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un ID.", Alert.AlertType.ERROR);
            return;
        }

        Medico medico = medicoLogica.findById(id);
        if (medico == null) {
            mostrarAlerta("Error", "Médico no encontrado.", Alert.AlertType.ERROR);
        } else {
            txtNombreMedico.setText(medico.getNombre());
            txtEspecialidadMedico.setText(medico.getEspecialidad());
            txtIdMedico.setText(String.valueOf(medico.getId()));
            txtIdMedico.setDisable(true);
            txtNombreMedico.setDisable(false);
            txtEspecialidadMedico.setDisable(false);
        }
    }

    @FXML
    private void mostrarReporte() {
        listaMedicos.setAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);
        limpiarCampos();
    }

    @FXML
    private void limpiarCampos() {
        txtIdMedico.clear();
        txtNombreMedico.clear();
        txtEspecialidadMedico.clear();
        refrescarTabla();
    }

    private void refrescarTabla() {
        listaMedicos.setAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}

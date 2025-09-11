package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;

import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class GestionMedicosController {

    private static final String PREFIJO_ID = "00"; // para médicos
    private static final String PREFIJO_ID_FARMACEUTA = "01"; // para médicos

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

    //Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabMedicos;
    @FXML private Tab tabPacientes;
    @FXML private Tab tabMedicamentos;
    @FXML private Tab tabDashboard;
    @FXML private Tab tabPrescribir;
    @FXML private Tab tabHistorico;
    @FXML private Tab tabAcercaDe;
    @FXML private Tab tabFarmaceutas;


    @FXML
    public void initialize() {
        medicoLogica = new MedicoLogica();

        //SECCION DE MEDICOS

        colIdMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecialidadMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));

        listaMedicos = FXCollections.observableArrayList();

        // Inicializar el campo de ID con el prefijo del módulo
        txtIdMedico.setText(PREFIJO_ID);

        // colocar el cursor al final
        txtIdMedico.positionCaret(PREFIJO_ID.length());

        // Evitar que el usuario borre el prefijo
        txtIdMedico.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith(PREFIJO_ID)) {
                txtIdMedico.setText(PREFIJO_ID);
                txtIdMedico.positionCaret(PREFIJO_ID.length());
            }
        });

        refrescarTabla();
    }

    private void ocultarSiNoTienePermiso(Tab tab, String codigo) {
        if (!Sesion.puedeAccederModulo(codigo)) {
            tabPane.getTabs().remove(tab); // lo elimina de la vista
        }
    }

    public void configurarSegunPermisos() {
        ocultarSiNoTienePermiso(tabPrescribir, "PRESCRIBIR");
        ocultarSiNoTienePermiso(tabMedicos, "GESTION_MEDICOS");
        ocultarSiNoTienePermiso(tabHistorico, "HISTORICO");
        ocultarSiNoTienePermiso(tabDashboard, "DASHBOARD");
        ocultarSiNoTienePermiso(tabAcercaDe, "ACERCA");
        ocultarSiNoTienePermiso(tabPacientes, "GESTION_PACIENTES");
        ocultarSiNoTienePermiso(tabMedicamentos, "GESTION_MEDICAMENTOS");
        ocultarSiNoTienePermiso(tabFarmaceutas, "GESTION_FARMACEUTAS");
    }

    @FXML
    private void agregarMedico() {
        String id = txtIdMedico.getText().trim();
        String nombre = txtNombreMedico.getText().trim();
        String especialidad = txtEspecialidadMedico.getText().trim();
        String numero = id.substring(PREFIJO_ID.length()); // lo que viene después del prefijo

        if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        if (!numero.matches("\\d+")) {
            mostrarAlerta("Error", "El ID debe contener solo números después de " + PREFIJO_ID, Alert.AlertType.ERROR);
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
                // Reiniciar ID para el próximo
                txtIdMedico.setText(PREFIJO_ID);
                txtIdMedico.positionCaret(PREFIJO_ID.length());
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

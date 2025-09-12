package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyStringWrapper;

public class GestionMedicosController implements Initializable {

    private static final String PREFIJO_ID = "MED";
    private static final String PREFIJO_ID_FARMACEUTA = "FAR";

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


    // Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabMedicos;
    @FXML private Tab tabPacientes;
    @FXML private Tab tabMedicamentos;
    @FXML private Tab tabDashboard;
    @FXML private Tab tabPrescribir;
    @FXML private Tab tabHistorico;
    @FXML private Tab tabAcercaDe;
    @FXML private Tab tabFarmaceutas;

    // Alamacenamiento de datos
    private final ObservableList<Medico> listaMedicos = FXCollections.observableArrayList();
    private static final String RUTA_MEDICOS = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "medicos.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_MEDICOS controller = " + RUTA_MEDICOS);
    }

    private final MedicoLogica medicoLogica = new MedicoLogica(RUTA_MEDICOS);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[DEBUG] Iniciando GestionMedicosController...");

        // Configuración básica que siempre funciona
        colIdMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecialidadMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));

        // Inicializar campo ID
        txtIdMedico.setText(PREFIJO_ID);
        txtIdMedico.positionCaret(PREFIJO_ID.length());

        // Listener para prefijo
        txtIdMedico.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith(PREFIJO_ID)) {
                txtIdMedico.setText(PREFIJO_ID);
                txtIdMedico.positionCaret(PREFIJO_ID.length());
            }
        });


        listaMedicos.addAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);

    }


    private void ocultarSiNoTienePermiso(Tab tab, String codigo) {
        if (!Sesion.puedeAccederModulo(codigo)) {
            tabPane.getTabs().remove(tab);
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
        try {
            String id = txtIdMedico.getText().trim();
            String nombre = txtNombreMedico.getText().trim();
            String especialidad = txtEspecialidadMedico.getText().trim();
            String numero = id.substring(PREFIJO_ID.length());

            if (id.isEmpty() || nombre.isEmpty() || especialidad.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            if (!numero.matches("\\d+")) {
                mostrarAlerta("Error", "El ID debe contener solo números después de " + PREFIJO_ID, Alert.AlertType.ERROR);
                return;
            }

            Medico nuevoMedico = new Medico(id, id, nombre, especialidad);

            if (medicoLogica.findById(id) != null) {
                medicoLogica.update(nuevoMedico);
                mostrarAlerta("Médico modificado", "El médico ha sido modificado con éxito.", Alert.AlertType.INFORMATION);
            } else {
                medicoLogica.create(nuevoMedico);
                mostrarAlerta("Médico agregado", "El médico ha sido agregado con éxito.", Alert.AlertType.INFORMATION);
            }

            limpiarCampos();
            refrescarTabla();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void eliminarMedico() {
        try {
            Medico seleccionado = tablaMedicos.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta(
                        "Selección requerida",
                        "Por favor, seleccione un médico de la tabla para eliminar.",
                        Alert.AlertType.WARNING
                );
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Desea eliminar al médico seleccionado?");
            confirmacion.setContentText("Médico: " + seleccionado.getNombre() + " (" + seleccionado.getId() + ")");

            // Mostrar y esperar confirmación
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    try {
                        medicoLogica.deleteById(seleccionado.getId());
                        mostrarAlerta("Éxito", "El médico ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
                        refrescarTabla();
                        limpiarCampos();
                    } catch (Exception e) {
                        mostrarAlerta("Error al eliminar", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            });

        } catch (Exception error) {
            mostrarAlerta("Error inesperado", "Ocurrió un error al intentar eliminar al médico. Inténtelo de nuevo.", Alert.AlertType.ERROR);
            error.printStackTrace();
        }
    }



    @FXML
    private void buscarMedico() {
        try {
            String criterio = txtBuscarMedico.getText().trim().toLowerCase();
            if(criterio.isEmpty())
            {
                tablaMedicos.setItems(listaMedicos);
                return;
            }

            ObservableList<Medico> filtrados =
                    FXCollections.observableArrayList(
                            listaMedicos.stream()
                                    .filter(m -> m.getId().toLowerCase().contains(criterio)
                                            || m.getNombre().toLowerCase().contains(criterio)
                                            || m.getEspecialidad().toLowerCase().contains(criterio))
                                    .collect(Collectors.toList())
                    );

            tablaMedicos.setItems(filtrados);
        }
        catch (Exception error)
        {
            mostrarAlerta("Error al buscar el cliente", "Volver a intentarlo",  Alert.AlertType.ERROR);
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
        txtIdMedico.setText(PREFIJO_ID);
        txtIdMedico.positionCaret(PREFIJO_ID.length());
        txtNombreMedico.clear();
        txtEspecialidadMedico.clear();
        txtBuscarMedico.clear();
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
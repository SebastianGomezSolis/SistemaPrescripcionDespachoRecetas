package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class GestionMedicosController {

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

    private ObservableList<Medico> listaMedicos;
    private MedicoLogica medicoLogica;
    private boolean xmlMode = false; // Flag para controlar modo XML

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

    // Lista temporal para fallback
    private static final List<Medico> medicosFallback = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("[DEBUG] Iniciando GestionMedicosController...");

        // Configuración básica que siempre funciona
        colIdMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecialidadMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));

        listaMedicos = FXCollections.observableArrayList();

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

        // Intentar inicializar XML, pero sin fallar si no funciona
        inicializarSistemaPersistencia();

        // Cargar datos iniciales
        refrescarTabla();

        System.out.println("[DEBUG] GestionMedicosController inicializado correctamente");
    }

    private void inicializarSistemaPersistencia() {
        try {
            // Intentar modo XML
            String rutaXML = java.nio.file.Paths
                    .get(System.getProperty("user.dir"), "bd", "medicos.xml")
                    .toString();

            System.out.println("[DEBUG] Intentando inicializar XML en: " + rutaXML);
            medicoLogica = new MedicoLogica(rutaXML);

            // Probar que funciona haciendo una operación simple
            medicoLogica.findAll();
            xmlMode = true;
            System.out.println("[DEBUG] Modo XML activado correctamente");

        } catch (Exception e) {
            System.err.println("[WARNING] No se pudo inicializar modo XML: " + e.getMessage());
            System.err.println("[INFO] Usando modo de memoria temporal");
            medicoLogica = null;
            xmlMode = false;
        }
    }

    // Método para obtener médicos de forma segura
    private List<Medico> obtenerMedicos() {
        if (xmlMode && medicoLogica != null) {
            try {
                return medicoLogica.findAll();
            } catch (Exception e) {
                System.err.println("[ERROR] Fallo en XML, cambiando a modo memoria: " + e.getMessage());
                xmlMode = false;
                medicoLogica = null;
            }
        }
        // Modo fallback con memoria
        return new ArrayList<>(medicosFallback);
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

        if (xmlMode && medicoLogica != null) {
            // Modo XML
            try {
                Medico existente = medicoLogica.findById(id);
                if (existente != null) {
                    medicoLogica.update(nuevoMedico);
                    mostrarAlerta("Éxito", "Médico modificado correctamente.", Alert.AlertType.INFORMATION);
                } else {
                    medicoLogica.create(nuevoMedico);
                    mostrarAlerta("Éxito", "Médico agregado correctamente.", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
                return;
            }
        } else {
            // Modo memoria (fallback)
            boolean existe = medicosFallback.removeIf(m -> m.getId().equals(id));
            medicosFallback.add(nuevoMedico);

            if (existe) {
                mostrarAlerta("Éxito", "Médico modificado (modo memoria).", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Éxito", "Médico agregado (modo memoria).", Alert.AlertType.INFORMATION);
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

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("Eliminar médico");
        confirm.setContentText("¿Está seguro que desea eliminar al médico con ID " + id + "?");

        if (confirm.showAndWait().get() != ButtonType.OK) {
            return;
        }

        try {
            if (xmlMode && medicoLogica != null) {
                medicoLogica.deleteById(id);
            } else {
                boolean removed = medicosFallback.removeIf(m -> m.getId().equals(id));
                if (!removed) {
                    throw new Exception("No existe un médico con este ID");
                }
            }

            refrescarTabla();
            limpiarCampos();
            mostrarAlerta("Éxito", "Médico eliminado correctamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarMedico() {
        String criterio = txtBuscarMedico.getText().trim();

        if (criterio.isEmpty()) {
            refrescarTabla();
            return;
        }

        try {
            List<Medico> resultados;

            if (xmlMode && medicoLogica != null) {
                resultados = medicoLogica.searchByNombreOEspecialidad(criterio);
            } else {
                // Búsqueda en memoria
                String busqueda = criterio.toLowerCase();
                resultados = medicosFallback.stream()
                        .filter(m -> m.getId().toLowerCase().contains(busqueda) ||
                                m.getNombre().toLowerCase().contains(busqueda) ||
                                m.getEspecialidad().toLowerCase().contains(busqueda))
                        .toList();
            }

            listaMedicos.setAll(resultados);
            tablaMedicos.setItems(listaMedicos);

            if (listaMedicos.isEmpty()) {
                mostrarAlerta("Sin resultados", "No se encontraron médicos con el criterio: " + criterio, Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al buscar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void mostrarReporte() {
        refrescarTabla();

        String modo = xmlMode ? "XML" : "Memoria";
        int total = listaMedicos.size();

        mostrarAlerta("Reporte",
                "Total de médicos: " + total + "\nModo de almacenamiento: " + modo,
                Alert.AlertType.INFORMATION);
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
        try {
            List<Medico> medicos = obtenerMedicos();
            listaMedicos.setAll(medicos);
            tablaMedicos.setItems(listaMedicos);
        } catch (Exception e) {
            System.err.println("Error refrescando tabla: " + e.getMessage());
            listaMedicos.clear();
            tablaMedicos.setItems(listaMedicos);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
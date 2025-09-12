package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.FarmaceutaLogica;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.PacienteLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionMedicosController implements Initializable {

    private static final String PREFIJO_ID = "MED";
    private static final String PREFIJO_ID_FARMACEUTA = "FAR";
    private static final String PREFIJO_ID_PACIENTE = "PAC";

    // Tabla y columnas
    @FXML private TableView<Medico> tablaMedicos;
    @FXML private TableColumn<Medico, String> colIdMedico;
    @FXML private TableColumn<Medico, String> colNombreMedico;
    @FXML private TableColumn<Medico, String> colEspecialidadMedico;

    @FXML private TableView<Farmaceuta> tablaFarmaceutas;
    @FXML private TableColumn<Farmaceuta, String> colIdFarmaceutas;
    @FXML private TableColumn<Farmaceuta, String> colNombreFarmaceutas;

    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, String> colIdPaciente;
    @FXML private TableColumn<Paciente, String> colNombrePaciente;
    @FXML private TableColumn<Paciente, String> colTelefonoPaciente;
    @FXML private TableColumn<Paciente, String> colFechaNacimientoPaciente;

    // Campos de texto
    @FXML private TextField txtIdMedico;
    @FXML private TextField txtNombreMedico;
    @FXML private TextField txtEspecialidadMedico;
    @FXML private TextField txtBuscarMedico;

    @FXML private TextField txtIdFarmaceutas;
    @FXML private TextField txtNombreFarmaceutas;
    @FXML private TextField txtBuscarFarmaceutas;

    @FXML private TextField txtIdPaciente;
    @FXML private TextField txtNombrePaciente;
    @FXML private TextField txtTelefonoPaciente;
    @FXML private DatePicker dtpFechaNacimientoPaciente;
    @FXML private TextField txtBuscarPaciente;

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
    private final ObservableList<Farmaceuta> listaFarmaceutas = FXCollections.observableArrayList();
    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    private static final String RUTA_MEDICOS = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "medicos.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_MEDICOS controller = " + RUTA_MEDICOS);
    }
    private static final String RUTA_FARMACEUTAS = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "farmaceutas.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_FARMACEUTAS controller = " + RUTA_FARMACEUTAS);
    }
    private static final String RUTA_PACIENTES = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "pacientes.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_PACIENTES controller = " + RUTA_PACIENTES);
    }

    private final MedicoLogica medicoLogica = new MedicoLogica(RUTA_MEDICOS);
    private final FarmaceutaLogica farmaceutaLogica = new FarmaceutaLogica(RUTA_FARMACEUTAS);
    private final PacienteLogica pacienteLogica = new PacienteLogica(RUTA_PACIENTES);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("[DEBUG] Iniciando GestionMedicosController...");

        // Configuración básica que siempre funciona
        colIdMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEspecialidadMedico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));

        colIdFarmaceutas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombreFarmaceutas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        colIdPaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colNombrePaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefonoPaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colFechaNacimientoPaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNacimiento() != null ? cellData.getValue().getFechaNacimiento().toString() : ""));

        // Inicializar campo ID
        txtIdMedico.setText(PREFIJO_ID);
        txtIdMedico.positionCaret(PREFIJO_ID.length());

        txtIdFarmaceutas.setText(PREFIJO_ID_FARMACEUTA);
        txtIdFarmaceutas.positionCaret(PREFIJO_ID_FARMACEUTA.length());

        txtIdPaciente.setText(PREFIJO_ID_PACIENTE);
        txtIdPaciente.positionCaret(PREFIJO_ID_PACIENTE.length());

        // Listener para prefijo
        txtIdMedico.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith(PREFIJO_ID)) {
                txtIdMedico.setText(PREFIJO_ID);
                txtIdMedico.positionCaret(PREFIJO_ID.length());
            }
        });

        txtIdFarmaceutas.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith(PREFIJO_ID_FARMACEUTA)) {
                txtIdFarmaceutas.setText(PREFIJO_ID_FARMACEUTA);
                txtIdFarmaceutas.positionCaret(PREFIJO_ID_FARMACEUTA.length());
            }
        });

        txtIdPaciente.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith(PREFIJO_ID_PACIENTE)) {
                txtIdPaciente.setText(PREFIJO_ID_PACIENTE);
                txtIdPaciente.positionCaret(PREFIJO_ID_PACIENTE.length());
            }
        });


        listaMedicos.addAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);

        listaFarmaceutas.addAll(farmaceutaLogica.findAll());
        tablaFarmaceutas.setItems(listaFarmaceutas);

        listaPacientes.addAll(pacienteLogica.findAll());
        tablaPacientes.setItems(listaPacientes);

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

            limpiarCamposMedico();
            refrescarTablaMedico();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void agregarFarmaceuta() {
        try {
            String id = txtIdFarmaceutas.getText().trim();
            String nombre = txtNombreFarmaceutas.getText().trim();
            String numero = id.substring(PREFIJO_ID_FARMACEUTA.length());

            if (id.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            if (!numero.matches("\\d+")) {
                mostrarAlerta("Error", "El ID debe contener solo números después de " + PREFIJO_ID_FARMACEUTA, Alert.AlertType.ERROR);
                return;
            }

            Farmaceuta nueveFarmaceuta = new Farmaceuta(id, id, nombre);

            if (farmaceutaLogica.findById(id) != null) {
                farmaceutaLogica.update(nueveFarmaceuta);
                mostrarAlerta("Farmaceuta modificado", "El farmaceuta ha sido modificado con éxito.", Alert.AlertType.INFORMATION);
            } else {
                farmaceutaLogica.create(nueveFarmaceuta);
                mostrarAlerta("Farmaceuta agregado", "El farmaceuta ha sido agregado con éxito.", Alert.AlertType.INFORMATION);
            }

            limpiarCamposFarmaceuta();
            refrescarTablaMedico();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void agregarPaciente() {
        try {
            String id = txtIdPaciente.getText().trim();
            String nombre = txtNombrePaciente.getText().trim();
            String telefono = txtTelefonoPaciente.getText().trim();
            LocalDate fechaNacimiento = dtpFechaNacimientoPaciente.getValue();
            String numero = id.substring(PREFIJO_ID_PACIENTE.length());

            if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || fechaNacimiento == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            if (!numero.matches("\\d+")) {
                mostrarAlerta("Error", "El ID debe contener solo números después de " + PREFIJO_ID_FARMACEUTA, Alert.AlertType.ERROR);
                return;
            }

            Paciente nuevoPaciente = new Paciente(id, nombre, fechaNacimiento, telefono);

            if (pacienteLogica.findById(id) != null) {
                pacienteLogica.update(nuevoPaciente);
                mostrarAlerta("Paciente modificado", "El paciente ha sido modificado con éxito.", Alert.AlertType.INFORMATION);
            } else {
                pacienteLogica.create(nuevoPaciente);
                mostrarAlerta("Paciente agregado", "El paciente ha sido agregado con éxito.", Alert.AlertType.INFORMATION);
            }

            limpiarCamposPaciente();
            refrescarTablaPaciente();
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
                        refrescarTablaMedico();
                        limpiarCamposMedico();
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
    private void eliminarFarmaceuta() {
        try {
            Farmaceuta seleccionado = tablaFarmaceutas.getSelectionModel().getSelectedItem();

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
            confirmacion.setContentText("Farmaceuta: " + seleccionado.getNombre() + " (" + seleccionado.getId() + ")");

            // Mostrar y esperar confirmacion
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    try {
                        farmaceutaLogica.deleteByID(seleccionado.getId());
                        mostrarAlerta("Éxito", "El farmaceuta ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
                        refrescarTablaFarmaceuta();
                        limpiarCamposFarmaceuta();
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
    private void eliminarPaciente() {
        try {
            Paciente seleccionado = tablaPacientes.getSelectionModel().getSelectedItem();

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
            confirmacion.setHeaderText("¿Desea eliminar al paciente seleccionado?");
            confirmacion.setContentText("Paciente: " + seleccionado.getNombre() + " (" + seleccionado.getId() + ")");

            // Mostrar y esperar confirmacion
            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    try {
                        pacienteLogica.deleteById(seleccionado.getId());
                        mostrarAlerta("Éxito", "El ppaciente ha sido eliminado correctamente.", Alert.AlertType.INFORMATION);
                        refrescarTablaPaciente();
                        limpiarCamposPaciente();
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
    private void buscarFarmaceuta() {
        try {
            String criterio = txtBuscarFarmaceutas.getText().trim().toLowerCase();
            if(criterio.isEmpty())
            {
                tablaFarmaceutas.setItems(listaFarmaceutas);
                return;
            }

            ObservableList<Farmaceuta> filtrados =
                    FXCollections.observableArrayList(
                            listaFarmaceutas.stream()
                                    .filter(f -> f.getId().toLowerCase().contains(criterio)
                                            || f.getNombre().toLowerCase().contains(criterio))
                                    .collect(Collectors.toList())
                    );

            tablaFarmaceutas.setItems(filtrados);
        } catch (Exception error) {
            mostrarAlerta("Error al buscar el cliente", "Volver a intentarlo", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarPaciente() {
        try {
            String criterio = txtBuscarPaciente.getText().trim().toLowerCase();
            if(criterio.isEmpty())
            {
                tablaPacientes.setItems(listaPacientes);
                return;
            }

            ObservableList<Paciente> filtrados =
                    FXCollections.observableArrayList(
                            listaPacientes.stream()
                                    .filter(p -> p.getId().toLowerCase().contains(criterio)
                                            || p.getNombre().toLowerCase().contains(criterio))
                                    .collect(Collectors.toList())
                    );
            tablaPacientes.setItems(filtrados);
        } catch (Exception error) {
            mostrarAlerta("Error al buscar el cliente", "Volver a intentarlo", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void mostrarReporteMedico() {
        listaMedicos.setAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);
        limpiarCamposMedico();
    }

    @FXML
    private void mostrarReporteFarmaceuta() {
        listaFarmaceutas.setAll(farmaceutaLogica.findAll());
        tablaFarmaceutas.setItems(listaFarmaceutas);
        limpiarCamposFarmaceuta();
    }

    @FXML
    private void mostrarReportePaciente() {
        listaPacientes.setAll(pacienteLogica.findAll());
        tablaPacientes.setItems(listaPacientes);
        limpiarCamposPaciente();
    }

    @FXML
    private void limpiarCamposMedico() {
        txtIdMedico.setText(PREFIJO_ID);
        txtIdMedico.positionCaret(PREFIJO_ID.length());
        txtNombreMedico.clear();
        txtEspecialidadMedico.clear();
        txtBuscarMedico.clear();
        refrescarTablaMedico();
    }

    @FXML
    private void limpiarCamposFarmaceuta() {
        txtIdFarmaceutas.setText(PREFIJO_ID_FARMACEUTA);
        txtIdFarmaceutas.positionCaret(PREFIJO_ID_FARMACEUTA.length());
        txtNombreFarmaceutas.clear();
        txtBuscarFarmaceutas.clear();
        refrescarTablaFarmaceuta();
    }

    @FXML
    private void limpiarCamposPaciente() {
        txtIdPaciente.setText(PREFIJO_ID_PACIENTE);
        txtIdPaciente.positionCaret(PREFIJO_ID_PACIENTE.length());
        txtNombrePaciente.clear();
        txtTelefonoPaciente.clear();
        dtpFechaNacimientoPaciente.setValue(null);
        txtBuscarPaciente.clear();
        refrescarTablaMedico();
    }

    private void refrescarTablaMedico() {
        listaMedicos.setAll(medicoLogica.findAll());
        tablaMedicos.setItems(listaMedicos);
    }

    private void refrescarTablaFarmaceuta() {
        listaFarmaceutas.setAll(farmaceutaLogica.findAll());
        tablaFarmaceutas.setItems(listaFarmaceutas);
    }

    private void refrescarTablaPaciente() {
        listaPacientes.setAll(pacienteLogica.findAll());
        tablaPacientes.setItems(listaPacientes);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
package com.sistema.sistemaprescripciondespachorecetas.controller;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.PacienteLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.RutasArchivos;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter; //convierte en string

public class BuscarPacientesController {
    @FXML private ComboBox<String>  CB_Nombre;
    @FXML private TextField TXF_Nombre;
    @FXML TableView<Paciente>  TV_Pacientes;
    @FXML private TableColumn<Paciente, String> colId;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, String> colTelefono;
    @FXML private TableColumn<Paciente, String> colFechaNacimiento;

    private final ObservableList<Paciente> listaObservable = FXCollections.observableArrayList();
    private PacienteLogica pacienteLogica;
    private Paciente pacienteSeleccionado;

    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    @FXML
    public void initialize() {
        pacienteLogica = new PacienteLogica(RutasArchivos.PACIENTES);

        CB_Nombre.getItems().addAll("ID", "Nombre");
        CB_Nombre.getSelectionModel().select("Nombre");

        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
        colFechaNacimiento.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        );

        TV_Pacientes.setItems(listaObservable);
        cargarPacientes();

        TXF_Nombre.textProperty().addListener((obs, oldVal, newVal) -> filtrar());
    }

    private void cargarPacientes() {
        listaObservable.setAll(pacienteLogica.findAll());
    }

    private void filtrar() {
        String texto = TXF_Nombre.getText().trim();

        if (texto.isEmpty()) {
            cargarPacientes();
            return;
        }

        String criterio = CB_Nombre.getSelectionModel().getSelectedItem();

        if ("ID".equals(criterio)) {
            listaObservable.setAll(
                    pacienteLogica.findAll().stream()
                            .filter(p -> p.getId().toLowerCase().contains(texto.toLowerCase()))
                            .toList()
            );
        } else {
            if (texto.length() == 1) {
                // Con una letra: buscar nombres que EMPIECEN por esa letra
                listaObservable.setAll(
                        pacienteLogica.findAll().stream()
                                .filter(p -> p.getNombre().toLowerCase().startsWith(texto.toLowerCase()))
                                .toList()
                );
            } else {
                // Con 2+ letras: buscar nombres que CONTENGAN el texto
                listaObservable.setAll(
                        pacienteLogica.findAll().stream()
                                .filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase()))
                                .toList()
                );
            }
        }
    }

    @FXML
    private void Aceptar() {
        pacienteSeleccionado = TV_Pacientes.getSelectionModel().getSelectedItem();
        if (pacienteSeleccionado != null) {
            TV_Pacientes.getScene().getWindow().hide();
        } else {
            mostrarAlerta("Debe seleccionar un paciente.");
        }
    }

    @FXML
    private void Cancelar() {
        pacienteSeleccionado = null;
        TV_Pacientes.getScene().getWindow().hide();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}

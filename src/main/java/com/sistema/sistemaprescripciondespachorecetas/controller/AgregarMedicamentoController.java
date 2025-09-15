package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicamentoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.model.RecetaDetalle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyStringWrapper;


public class AgregarMedicamentoController implements Initializable {

    @FXML private TableView<Medicamento> TV_Medicamento;
    @FXML private TableColumn<Medicamento, String> colCodigo;
    @FXML private TableColumn<Medicamento, String> colNombre;
    @FXML private TableColumn<Medicamento, String> colPresentacion;


    @FXML private TextField TXF_NombreMedicamento ;

    private Paciente pacienteSeleccionado;
    private Receta recetaActual;

    private ObservableList<Medicamento> listaMedicamentos = FXCollections.observableArrayList();
    MedicamentoLogica logica = new MedicamentoLogica(
            Paths.get(System.getProperty("user.dir"), "bd", "medicamentos.xml").toString()
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCodigo.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getCodigo()));
        colNombre.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getNombre()));
        colPresentacion.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getDescripcion()));
        listaMedicamentos.addAll(logica.findAll());
        TV_Medicamento.setItems(listaMedicamentos);
    }

    public void setPacienteSeleccionado(Paciente paciente) {
        this.pacienteSeleccionado = paciente;
    }

    public void setRecetaActual(Receta receta) {
        this.recetaActual = receta;
    }

    @FXML
    public void aceptarMedicamento() {
        Medicamento seleccionado = TV_Medicamento.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un medicamento.");
            return;
        }

        if (recetaActual == null) {
            recetaActual = new Receta();
            recetaActual.setPaciente(pacienteSeleccionado);
            recetaActual.setFechaEntrega(LocalDate.now());
            recetaActual.setEstado("Confeccionada");
            recetaActual.setMedicamentos(new ArrayList<>());
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com.sistema.sistemaprescripciondespachorecetas/view/Receta.fxml")
            );
            Parent root = loader.load();

            RecetaController controller = loader.getController();
            controller.setMedicamentoSeleccionado(seleccionado);
            controller.setPacienteSeleccionado(pacienteSeleccionado);
            controller.setRecetaActual(recetaActual);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Detalle del Medicamento");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com.sistema.sistemaprescripciondespachorecetas/images/Detalle-medicamento-busqueda.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            Object userData = stage.getUserData();
            if (userData instanceof Receta) {
                recetaActual = (Receta) userData;

                Stage currentStage = (Stage) TV_Medicamento.getScene().getWindow();
                currentStage.setUserData(recetaActual);
                currentStage.close();
            }

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario de detalle: " + e.getMessage());
        }
    }


    @FXML
    private void buscarMedicamento(){
        try {
            String criterio = TXF_NombreMedicamento.getText().trim().toLowerCase();
            if(criterio.isEmpty())
            {
                TV_Medicamento.setItems(listaMedicamentos);
                return;
            }

            ObservableList<Medicamento> filtrados =
                    FXCollections.observableArrayList(
                            listaMedicamentos.stream()
                                    .filter(c -> c.getCodigo().toLowerCase().contains(criterio)
                                            || c.getNombre().toLowerCase().contains(criterio)
                                            || c.getDescripcion().toLowerCase().contains(criterio))
                                    .collect(Collectors.toList())
                    );

            TV_Medicamento.setItems(filtrados);
        }
        catch (Exception error)
        {
            mostrarAlerta("Error al buscar el medicamento", error.getMessage());
        }
    }

    @FXML
    public void cancelarMedicamento() {
        Stage stage = (Stage) TV_Medicamento.getScene().getWindow();
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

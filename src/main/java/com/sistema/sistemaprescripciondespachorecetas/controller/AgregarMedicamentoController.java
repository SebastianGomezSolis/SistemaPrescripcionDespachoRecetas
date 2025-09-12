package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicamentoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyStringWrapper;


public class AgregarMedicamentoController implements Initializable {

    // Tabla y columnas
    @FXML private TableView<Medicamento> TV_Medicamento;
    @FXML private TableColumn<Medicamento, String> colCodigo;
    @FXML private TableColumn<Medicamento, String> colNombre;
    @FXML private TableColumn<Medicamento, String> colPresentacion;

    // Campos de texto
    @FXML private TextField TXF_NombreMedicamento ;
    @FXML private SplitMenuButton SPB_NombreMedicamento;

    @FXML private ObservableList<Medicamento> listaMedicamentos;
    private MedicamentoLogica medicamentoLogica;

    private static final String RUTA_MEDICAMENTOS= java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "medicamentos.xml")
            .toString();
    {
        System.out.println("[DEBUG] RUTA_MEDICAMENTOS controller = " + RUTA_MEDICAMENTOS);
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        colCodigo.setCellValueFactory(cd->
                new ReadOnlyStringWrapper(String.valueOf(cd.getValue().getCodigo()))
        );

        colNombre.setCellValueFactory(cd ->
                new ReadOnlyStringWrapper(cd.getValue().getNombre() + " " + cd.getValue().getNombre())
        );

        colPresentacion.setCellValueFactory(cd->
                new ReadOnlyStringWrapper(cd.getValue().getDescripcion())
        );

        listaMedicamentos.addAll(medicamentoLogica.findAll()); // sospechoso
        TV_Medicamento.setItems(listaMedicamentos);

    }

    @FXML
    private void aceptarMedicamento(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sistema/sistemaprescripciondespachorecetas/view/Receta.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) TV_Medicamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Medicamento");
            stage.show();
        }
       catch(IOException e){
           mostrarAlerta("Error al abrir el formulario", e.getMessage());
       }


    }

    public void cancelarMedicamento() {
        try
        {
            Stage stage = (Stage) TV_Medicamento.getScene().getWindow();
            stage.setUserData(null);
            stage.close();
        }
        catch (Exception error) {
            mostrarAlerta("Error al aceptar", error.getMessage());
        }
    }

    @FXML
    private void buscarMedicamento(){
        try {
            String criterio = SPB_NombreMedicamento.getText().trim().toLowerCase();
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}

package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.List;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML Button btnIniciarSesion;
    @FXML Button btnCambiarClave;
    @FXML ProgressIndicator progressInicioSesion;
    @FXML ProgressIndicator progressCambiarClave;

    //Tabs
    @FXML private TabPane tabPane;
    @FXML private Tab tabMedicos;
    @FXML private Tab tabPacientes;
    @FXML private Tab tabMedicamentos;
    @FXML private Tab tabDashboard;
    @FXML private Tab tabPrescribir;
    @FXML private Tab tabHistorico;
    @FXML private Tab tabAcercaDe;



    private void ocultarSiNoTienePermiso(Tab tab, String codigo) {
        if (!Sesion.puedeAccederModulo(codigo)) {
            tabPane.getTabs().remove(tab); // lo elimina de la vista
        }
    }


    @FXML
    protected void handleLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        // Mostrar progress y ocultar botón
        btnIniciarSesion.setVisible(false);
        progressInicioSesion.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simula procesamiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                progressInicioSesion.setVisible(false);
                btnIniciarSesion.setVisible(true);

                // Aquí validamos credenciales
                if (validarCredenciales(usuario, password)) {

                    // Detectar permisos según prefijo ID (2 primeros caracteres)
                    List<String> permisos = asignarPermisosPorPrefijo(usuario);

                    // Iniciar sesión con permisos asignados
                    Sesion.iniciarSesion(usuario, permisos);

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sistema.sistemaprescripciondespachorecetas/view/GestionMedicos.fxml"));
                        Parent root = loader.load();

                        GestionMedicosController controller = loader.getController();
                        controller.configurarSegunPermisos();

                        Stage stage = (Stage) txtUsuario.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Menú principal");

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error de sistema");
                        alert.setHeaderText(e.getStackTrace()[0].getClassName());
                        alert.setContentText("No fue posible iniciar sesión, debido a un error de sistema: " + e.getMessage());
                        alert.showAndWait();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de autenticación");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuario o contraseña incorrectos.");
                    alert.showAndWait();
                }
            });
        }).start();
    }

    // Método simple de validación (puedes reemplazar con tu lógica)
    private boolean validarCredenciales(String usuario, String password) {
        // Ejemplo simple: usuario no vacío y password '001234' para todos
        return !usuario.isEmpty() && password.equals("001234");
    }

    private List<String> asignarPermisosPorPrefijo(String usuario) {
        if (usuario.length() < 2) {
            return List.of();
        }
        String prefijo = txtPassword.getText().substring(0, 2);

        List<String> permisos = switch (prefijo) {
            case "00" -> List.of("PRESCRIBIR", "HISTORICO", "DASHBOARD", "ACERCA"); // Médico
            case "01" -> List.of("DESPACHAR", "HISTORICO", "DASHBOARD", "ACERCA");  // Farmacéutico
            case "99" -> List.of("GESTION_MEDICOS", "GESTION_FARMACEUTAS" ,"GESTION_PACIENTES", "GESTION_MEDICAMENTOS", "DASHBOARD", "HISTORICO", "ACERCA"); // Admin
            default -> List.of();
        };
        System.out.println("Permisos asignados: " + permisos);
        return permisos;
    }



    @FXML
    protected void funcionCambiarClave() {
        // Mostrar progress y ocultar botón
        btnCambiarClave.setVisible(false);
        progressCambiarClave.setVisible(true);

        // Simular un pequeño retardo como si validara contra un servidor
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simula procesamiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                progressCambiarClave.setVisible(false);
                btnCambiarClave.setVisible(true);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sistema.sistemaprescripciondespachorecetas/view/CambiarClave.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Menu cambio clave");
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de sistema");
                    alert.setHeaderText(e.getStackTrace()[0].getClassName());
                    alert.setContentText("No fue posible ir al menu de cambio de clave, debido a un error de sistema: " + e.getMessage());
                    alert.showAndWait();
                }
            });
        }).start();
    }

    @FXML
    protected void salirLogin() {
        System.exit(0);
    }
}
package com.sistema.sistemaprescripciondespachorecetas.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML Button btnIniciarSesion;
    @FXML Button btnCambiarClave;
    @FXML ProgressIndicator progressInicioSesion;
    @FXML ProgressIndicator progressCambiarClave;

    @FXML
    protected void handleLogin() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        // Mostrar progress y ocultar botón
        btnIniciarSesion.setVisible(false);
        progressInicioSesion.setVisible(true);

        // Simular un pequeño retardo como si validara contra un servidor
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simula procesamiento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                progressInicioSesion.setVisible(false);
                btnIniciarSesion.setVisible(true);
                if (usuario.equals("admin") && password.equals("1234")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sistema.sistemaprescripciondespachorecetas/view/GestionMedicos.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) txtUsuario.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Menu principal");
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
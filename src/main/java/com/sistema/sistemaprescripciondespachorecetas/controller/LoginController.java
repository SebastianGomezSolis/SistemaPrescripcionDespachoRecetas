package com.sistema.sistemaprescripciondespachorecetas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    protected void handleLogin() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if ("admin".equals(usuario) && "1234".equals(password)) {
            lblMensaje.setText("¡Bienvenido!");
        } else {
            lblMensaje.setText("Credenciales incorrectas.");
        }
    }

    @FXML
    protected void salirLogin() {
        System.exit(0);
    }
}
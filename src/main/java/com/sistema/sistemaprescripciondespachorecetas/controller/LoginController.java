package com.sistema.sistemaprescripciondespachorecetas.controller;

import com.sistema.sistemaprescripciondespachorecetas.logic.logica.AdministradorLogica;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.FarmaceutaLogica;
import com.sistema.sistemaprescripciondespachorecetas.logic.logica.MedicoLogica;
import com.sistema.sistemaprescripciondespachorecetas.model.Administrador;
import com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import com.sistema.sistemaprescripciondespachorecetas.model.Usuario;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.RutasArchivos;
import com.sistema.sistemaprescripciondespachorecetas.utilitarios.Sesion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.List;

public class LoginController implements Initializable {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verificarAdministradorPorDefecto();
    }


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

                Usuario usuarioAutenticado = validarCredenciales(usuario, password);
                if (usuarioAutenticado != null) {
                    List<String> permisos = asignarPermisosPorPrefijo(usuarioAutenticado.getId());
                    Sesion.iniciarSesion(usuarioAutenticado, permisos);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sistema.sistemaprescripciondespachorecetas/view/GestionMedicos.fxml"));
                        Parent root = loader.load();

                        GestionMedicosController controller = loader.getController();
                        controller.configurarSegunPermisos();

                        Stage stage = (Stage) txtUsuario.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com.sistema.sistemaprescripciondespachorecetas/images/hospital.png")));
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

    private Usuario validarCredenciales(String id, String password) {
        if (id == null || id.isBlank() || password == null || password.isBlank()) return null;

        try {
            if (id.startsWith("MED")) {
                Medico medico = new MedicoLogica(RutasArchivos.MEDICOS).findById(id);
                if (medico != null && medico.getClave().equals(password)) {
                    return medico;
                }

            } else if (id.startsWith("FAR")) {
                Farmaceuta farma = new FarmaceutaLogica(RutasArchivos.FARMACEUTAS).findById(id);
                if (farma != null && farma.getClave().equals(password)) {
                    return farma;
                }

            } else if (id.startsWith("ADM")) {
                Administrador admin = new AdministradorLogica(RutasArchivos.ADMINISTRADORES).findById(id);
                if (admin != null && admin.getClave().equals(password)) {
                    return admin;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private List<String> asignarPermisosPorPrefijo(String usuario) {
        if (usuario.length() < 3) {
            return List.of();
        }
        String prefijo = usuario.substring(0, 3);

        List<String> permisos = switch (prefijo) {
            case "MED" -> List.of("PRESCRIBIR", "HISTORICO", "DASHBOARD", "ACERCA"); // Médico
            case "FAR" -> List.of("HISTORICO", "DASHBOARD", "ACERCA", "GESTION_DESPACHO");  // Farmacéutico
            case "ADM" -> List.of("GESTION_MEDICOS", "GESTION_FARMACEUTAS" ,"GESTION_PACIENTES", "GESTION_MEDICAMENTOS", "DASHBOARD", "HISTORICO", "ACERCA"); // Admin
            default -> List.of();
        };
        return permisos;
    }

    @FXML
    protected void funcionCambiarClave() {
        // Validar que hay un usuario ingresado
        String usuario = txtUsuario.getText().trim();
        if (usuario.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario requerido");
            alert.setHeaderText(null);
            alert.setContentText("Debe ingresar su ID de usuario antes de cambiar la clave.");
            alert.showAndWait();
            return;
        }

        btnCambiarClave.setVisible(false);
        progressCambiarClave.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                progressCambiarClave.setVisible(false);
                btnCambiarClave.setVisible(true);
                try {
                    // Lista de todas las rutas
                    String[] rutasPosibles = {
                            "/com/sistema/sistemaprescripciondespachorecetas/view/CambiarClave.fxml",
                            "/com.sistema.sistemaprescripciondespachorecetas/view/CambiarClave.fxml",
                            "/view/CambiarClave.fxml",
                            "/CambiarClave.fxml",
                            "view/CambiarClave.fxml",
                            "CambiarClave.fxml",
                            "/fxml/CambiarClave.fxml",
                            "fxml/CambiarClave.fxml"
                    };

                    FXMLLoader loader = null;
                    String rutaEncontrada = null;

                    for (String ruta : rutasPosibles) {
                        try {
                            java.net.URL url = getClass().getResource(ruta);
                            if (url != null) {
                                System.out.println("ENCONTRADO en: " + ruta);
                                System.out.println("URL completa: " + url);
                                loader = new FXMLLoader(url);
                                rutaEncontrada = ruta;
                                break;
                            } else {
                                System.out.println("No encontrado en: " + ruta);
                            }
                        } catch (Exception e) {
                            System.out.println("Error en ruta: " + ruta + " - " + e.getMessage());
                        }
                    }

                    if (loader == null) {
                        System.out.println("ARCHIVO NO ENCONTRADO EN NINGUNA UBICACIÓN");
                        System.out.println("Verifica que el archivo CambiarClave.fxml existe en:");
                        System.out.println("src/main/resources/com/sistema/sistemaprescripciondespachorecetas/view/CambiarClave.fxml");

                        throw new Exception("No se pudo encontrar CambiarClave.fxml en ninguna ubicación");
                    }

                    System.out.println("CARGANDO ARCHIVO DESDE: " + rutaEncontrada + "");
                    Parent root = loader.load();

                    // *** PARTE IMPORTANTE: Pasar el usuario al controlador ***
                    CambiarClaveController controller = loader.getController();
                    controller.setUsuarioId(usuario.toUpperCase());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Cambio de clave");
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    System.out.println("Ventana abierta exitosamente");

                } catch (Exception e) {
                    System.out.println("ERROR FINAL");
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de sistema");
                    alert.setHeaderText("Error al abrir ventana");
                    alert.setContentText("No fue posible abrir el menú de cambio de clave: " + e.getMessage());
                    alert.showAndWait();
                }
            });
        }).start();
    }

    @FXML
    protected void salirLogin() {
        System.exit(0);
    }

    private void verificarAdministradorPorDefecto() {
        try {
            AdministradorLogica logica = new AdministradorLogica(RutasArchivos.ADMINISTRADORES);
            List<Administrador> existentes = logica.findAll();

            if (existentes.isEmpty()) {
                Administrador admin = new Administrador("ADM001", "ADM001");
                logica.create(admin);
            } else {
                // Verificar si existe el administrador ADM001, si no, crearlo
                Administrador adminTest = logica.findById("ADM001");
                if (adminTest == null) {
                    // Limpiar administradores incorrectos si existen
                    try {
                        logica.deleteById("Admin");
                    } catch (Exception e) {
                        // Ignorar si no existe
                    }

                    // Crear el administrador correcto
                    Administrador admin = new Administrador("ADM001", "ADM001");
                    logica.create(admin);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al verificar administrador por defecto: " + e.getMessage());
        }
    }

}
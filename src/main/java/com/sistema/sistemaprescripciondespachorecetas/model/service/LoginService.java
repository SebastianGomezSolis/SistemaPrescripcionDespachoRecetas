package com.sistema.sistemaprescripciondespachorecetas.model.service;

import com.sistema.sistemaprescripciondespachorecetas.model.access.AdministradorAccess;
import com.sistema.sistemaprescripciondespachorecetas.model.access.FarmaceutaAccess;
import com.sistema.sistemaprescripciondespachorecetas.model.access.MedicoAccess;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Administrador;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Farmaceuta;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Medico;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Usuario;

public class LoginService {
    private AdministradorAccess administradorAccess;
    private MedicoAccess medicoAccess;
    private FarmaceutaAccess farmaceutaAccess;

    // Tipos de usuario
    public static final String tipoAdministrador = "Administrador";
    public static final String tipoMedico = "Medico";
    public static final String tipoFarmaceuta = "Farmaceuta";
    public static final String tipoDesconocido = "Desconodido";

    // Resultados de autenticación
    public static final String autenExitosa = "Exitoso";
    public static final String autenUsuaNoEncontado = "Usuario_no_encontrado";
    public static final String autenInvalidPassword = "Clave_incorrecta";
    public static final String autenDatosInvalidos = "Datos_invalidos";
    public static final String autenErrorSistema = "Error_sistema";

    public LoginService() {
        try {
            this.administradorAccess = new AdministradorAccess();
            this.medicoAccess = new MedicoAccess();
            this.farmaceutaAccess = new FarmaceutaAccess();
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando el servicio de login: " + e.getMessage(), e);
        }
    }

    public Usuario autenticarUsuario(String id, String clave) {
        try {
            if (!validarCredenciales(id, clave)) {
                return null;
            }

            // Buscar en administradores
            Administrador admin = administradorAccess.buscarPorId(id);
            if (admin != null && validarClave(admin.getClave(), clave)) {
                return admin;
            }

            // Buscar en médicos
            Medico medico = medicoAccess.buscarMedicoPorId(id);
            if (medico != null && validarClave(medico.getClave(), clave)) {
                return medico;
            }

            // Buscar en farmaceutas
            Farmaceuta farmaceuta = farmaceutaAccess.buscarFarmaceutaPorId(id);
            if (farmaceuta != null && validarClave(farmaceuta.getClave(), clave)) {
                return farmaceuta;
            }

            return null;
        } catch (Exception e) {
            System.err.println("Error durante autenticación: " + e.getMessage());
            return null;
        }
    }

    public String obtenerResultadoAutenticacion(String id, String clave) {
        try {
            if (!validarCredenciales(id, clave)) {
                return autenDatosInvalidos;
            }

            boolean usuarioEncontrado = false;

            // Verificar administradores
            Administrador admin = administradorAccess.buscarPorId(id);
            if (admin != null) {
                usuarioEncontrado = true;
                return validarClave(admin.getClave(), clave) ? autenExitosa : autenInvalidPassword;
            }

            // Verificar médicos
            Medico medico = medicoAccess.buscarMedicoPorId(id);
            if (medico != null) {
                usuarioEncontrado = true;
                return validarClave(medico.getClave(), clave) ? autenExitosa : autenInvalidPassword;
            }

            // Verificar farmaceutas
            Farmaceuta farmaceuta = farmaceutaAccess.buscarFarmaceutaPorId(id);
            if (farmaceuta != null) {
                usuarioEncontrado = true;
                return validarClave(farmaceuta.getClave(), clave) ? autenExitosa : autenInvalidPassword;
            }

            return autenUsuaNoEncontado;
        } catch (Exception e) {
            System.err.println("Error durante verificación de autenticación: " + e.getMessage());
            return autenErrorSistema;
        }
    }

    public String obtenerTipoUsuario(Usuario usuario) {
        if (usuario == null) {
            return tipoDesconocido;
        }

        try {
            if (usuario instanceof Administrador) {
                return tipoAdministrador;
            } else if (usuario instanceof Medico) {
                return tipoMedico;
            } else if (usuario instanceof Farmaceuta) {
                return tipoFarmaceuta;
            }
            return tipoDesconocido;
        } catch (Exception e) {
            System.err.println("Error determinando tipo de usuario: " + e.getMessage());
            return tipoDesconocido;
        }
    }

    public boolean esAdministrador(Usuario usuario) {
        return tipoAdministrador.equals(obtenerTipoUsuario(usuario));
    }

    public boolean esMedico(Usuario usuario) {
        return tipoMedico.equals(obtenerTipoUsuario(usuario));
    }

    public boolean esFarmaceuta(Usuario usuario) {
        return tipoFarmaceuta.equals(obtenerTipoUsuario(usuario));
    }

    public boolean existeUsuario(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return false;
            }

            return administradorAccess.buscarPorId(id) != null ||
                    medicoAccess.buscarMedicoPorId(id) != null ||
                    farmaceutaAccess.buscarFarmaceutaPorId(id) != null;
        } catch (Exception e) {
            System.err.println("Error verificando existencia de usuario: " + e.getMessage());
            return false;
        }
    }

    public String obtenerMensajeError(String resultado) {
        switch (resultado) {
            case autenExitosa:
                return "Autenticación exitosa";
            case autenUsuaNoEncontado:
                return "Usuario no encontrado en el sistema";
            case autenInvalidPassword:
                return "La clave ingresada es incorrecta";
            case autenDatosInvalidos:
                return "Los datos ingresados no son válidos";
            case autenErrorSistema:
                return "Error interno del sistema. Contacte al administrador";
            default:
                return "Error desconocido";
        }
    }

    // Métodos privados de validación
    private boolean validarCredenciales(String id, String clave) {
        return id != null && !id.trim().isEmpty() &&
                clave != null && !clave.trim().isEmpty();
    }

    private boolean validarClave(String claveAlmacenada, String claveIngresada) {
        if (claveAlmacenada == null || claveIngresada == null) {
            return false;
        }
        return claveAlmacenada.equals(claveIngresada);
    }

}

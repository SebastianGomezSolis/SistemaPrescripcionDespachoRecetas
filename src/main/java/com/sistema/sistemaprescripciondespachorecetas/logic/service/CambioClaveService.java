package com.sistema.sistemaprescripciondespachorecetas.logic.service;


import com.sistema.sistemaprescripciondespachorecetas.logic.access.AdministradorAccess;
import com.sistema.sistemaprescripciondespachorecetas.logic.access.FarmaceutaAccess;
import com.sistema.sistemaprescripciondespachorecetas.logic.access.MedicoAccess;

public class CambioClaveService {
    private AdministradorAccess administradorAccess;
    private MedicoAccess medicoAccess;
    private FarmaceutaAccess farmaceutaAccess;

    // Resultados del cambio de clave
    public static final String cambioExitoso = "Exitoso";
    public static final String usuarioNoEncontrado = "Usuario_no_encontrado";
    public static final String claveActualIncorrecta = "Clave_actual_incorrecta";
    public static final String claveNuevaInvalida = "Nueva_clave_invalida";
    public static final String clavesIguales = "Claves_iguales";
    public static final String datosInvalidos = "Datos_invalidos";
    public static final String errorSistema = "Error_sistema";

    public CambioClaveService() {
        try {
            this.administradorAccess = new AdministradorAccess();
            this.medicoAccess = new MedicoAccess();
            this.farmaceutaAccess = new FarmaceutaAccess();
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando el servicio de cambio de clave: " + e.getMessage(), e);
        }
    }

    public boolean cambiarClave(String id, String claveActual, String nuevaClave) {
        String resultado = validarCambioClave(id, claveActual, nuevaClave);
        return cambioExitoso.equals(resultado);
    }

    public String validarCambioClave(String id, String claveActual, String nuevaClave) {
        try {
            // Validaciones básicas
            if (!validarDatosEntrada(id, claveActual, nuevaClave)) {
                return datosInvalidos;
            }

            // Verificar que la nueva clave no sea igual a la actual
            if (claveActual.equals(nuevaClave)) {
                return clavesIguales;
            }

            // Validar que la nueva clave cumpla con los criterios
            if (!validarNuevaClave(nuevaClave)) {
                return claveNuevaInvalida;
            }

            // Intentar cambiar clave en cada tipo de usuario
            if (administradorAccess.actualizarClave(id, claveActual, nuevaClave)) {
                return cambioExitoso;
            }

            if (medicoAccess.actualizarClave(id, claveActual, nuevaClave)) {
                return cambioExitoso;
            }

            if (farmaceutaAccess.actualizarClave(id, claveActual, nuevaClave)) {
                return cambioExitoso;
            }

            // Si llegamos aquí, el usuario no fue encontrado o la clave actual es incorrecta
            // Verificar si el usuario existe para dar un mensaje específico
            if (existeUsuario(id)) {
                return claveActualIncorrecta;
            } else {
                return usuarioNoEncontrado;
            }

        } catch (Exception e) {
            System.err.println("Error durante cambio de clave: " + e.getMessage());
            return errorSistema;
        }
    }

    private boolean existeUsuario(String id) {
        try {
            return administradorAccess.buscarPorId(id) != null || medicoAccess.buscarMedicoPorId(id) != null || farmaceutaAccess.buscarFarmaceutaPorId(id) != null;
        } catch (Exception e) {
            System.err.println("Error verificando existencia de usuario: " + e.getMessage());
            return false;
        }
    }

    public String obtenerMensajeResultado(String resultado) {
        switch (resultado) {
            case cambioExitoso:
                return "Clave cambiada exitosamente";
            case usuarioNoEncontrado:
                return "Usuario no encontrado en el sistema";
            case claveActualIncorrecta:
                return "La clave actual es incorrecta";
            case claveNuevaInvalida:
                return "La nueva clave no cumple con los criterios de seguridad";
            case clavesIguales:
                return "La nueva clave debe ser diferente a la actual";
            case datosInvalidos:
                return "Todos los campos son obligatorios";
            case errorSistema:
                return "Error interno del sistema. Contacte al administrador";
            default:
                return "Error desconocido";
        }
    }

    public String obtenerCriteriosClave() {
        return "La nueva clave debe:\n"  +
                "- No contener solo espacios en blanco\n" +
                "- Ser diferente a la clave actual";
    }

    // Métodos privados de validación
    private boolean validarDatosEntrada(String id, String claveActual, String nuevaClave) {
        return id != null && !id.trim().isEmpty() &&
                claveActual != null && !claveActual.trim().isEmpty() &&
                nuevaClave != null && !nuevaClave.trim().isEmpty();
    }

    private boolean validarNuevaClave(String nuevaClave) {
        // Criterios básicos de validación
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            return false;
        }

        // No debe ser solo espacios
        if (nuevaClave.trim().length() == 0) {
            return false;
        }

        return true;
    }
}
